# Spring Boot REST API

## The Purpose of the Application
Configuring a Spring Boot REST API that utilizes Keycloak as an Authorization server. The general idea is utilizing Keycloak as a way to obtain Access, Refresh and ID tokens that can then be used to authorize request to protected content in our resource server.

## The Advantages of Keycloak
In the versions of Spring Boot 2.x and onwards the best way to integrate Keycloak in our application was with the use of the [Keycloak Client Adapter](https://mvnrepository.com/artifact/org.keycloak/keycloak-spring-security-adapter) library.

Unfortunately with the advent of Spring Boot 3 most of its classes and method became deprecated, but it's not so much of a problem, as Keycloak still provides a multitude of **endpoints** that can allow us to manually execute authentication tasks.

## Setting Up a Keycloak Server
To utilize the services that Keycloak provides we firstly have to start a server that for this example will be run locally on the port **8081**.

The server can either be downloaded from their [official website](https://www.keycloak.org/downloads) or in the releases of their [GitHub repository](https://github.com/keycloak/keycloak/releases). The version I ended up using was Keycloak 20.0.3, but it shouldn't matter too much as long as it is one of the latest releases.

Once the compressed file is downloaded you can just unzip it and run the `kc.bat` (or `kc.sh` for Mac users) situated in the **bin** folder. It is recommended to start the server via terminal as it allows you to specify some essential parameters such as the `http-port`.

My command would result : `bin/kc.sh start-dev --http-port=8081`

Now if we reach to the [http://localhost:8081]() URL we should be presented with this :

![Welcome to Keycloak](./img/Welcome%20to%20Keycloak.png)

There we have to access the **Administration Console** to which we have to register with username and password credentials.
Its going to be the Administration Console that will allow us to manage the Keycloak server.

### Creating a Realm
The first step to manage one or more applications is creating a **Realm**. A Realm is basically a group of clients and users under the same "roof" to which are applied a common set of rules. We can also image it as a whole organization that can manage a wide range of web apps (named **Clients**).

<div float=left align="center">
<img src="/img/Keycloak Administration Console.png" width="20%"/>
<img src="/img/Keycloak Create Realm.png" width="20%"/>
</div>

Click on the **master** option at the top of left menu, and there it will allow you to create a new realm. 

![Realm Creation Page](img/Keycloak%20Realm%20Creation%20Page.png)

Fill in the "name" field with the name of your realm, I went with `SpringBootKeycloak`, and then click "Create".

### Creating  a Client
Next step is to create a **Client** by navigating onto the "Client" item in the left sidebar.
As stated by Keycloak, Clients are applications and services that can request authentication of a user, therefore its essential to associate one with our application.

Once into the Clients tab you will be presented with a list of active clients and a "Create Client" button at the top. Click it and insert the "Client ID" which is essential to let our application know to which client we are requesting access to, and i went with `web-app-1`.
You can also provide a formal name and a description which is optional.

![Keycloak Client Creation Page 1](img/Keycloak%20Client%20Creation%20Page%201.png)

When clicking the "Next" button Keycloak will also prompt you with the possibility of configuring some initial security settings for your client, like turning your "Client Authentication" On that will render your client private and render it only accessible only via a **client secret** code.

![Keycloak Client Creation Page 2](img/Keycloak%20Client%20Creation%20Page%202.png)

But for now we don't care about that so just click "Save" and it will be all good. 

Then for some additional configuration settings on your client page scroll down to **Access Settings** and set the "Valid Redirect URIs" and "Valid Post Logout Redirect URIs" and I'll set them both to [http://localhost:8080/*]() as **8080** will be the port i will be hosting my application at.

### Creating Roles
Roles are essentials for defining the hierarchy of both the Realm and the Clients, and they allow to restrict certain users from accessing protected content that they shouldn't have access to.

There are 2 main role scopes :
- **Realm Roles** $\rightarrow$ Global roles defined for the whole realms and accessible by all clients.
- **Client Roles** $\rightarrow$ Roles accessible only within the same client and can't be seen from other clients.

You can read more about the subject in this [Stack Overflow thread](https://stackoverflow.com/a/74956983).

Navigate to your created client settings, and into the **Roles** tab click "Create Role". I will be creating 2 roles :
- **user** $\rightarrow$ The client user role.
- **admin** $\rightarrow$ The client admin role.

Then navigate to **Realm Roles** in the left sidebar and there we'll create the **GLOBAL_ADMIN** role. This role should be able to access all the features of all the clients that the Realm will be providing authorization to.

Because of this after its creation we'll navigate in the top right **Action** item and click "Add associated roles". Filter by clients, select the `web-app-1` **user** role and the `web-app-1` **admin** role and click "Assign".

![Create Composite Roles](img/Composite%20Roles.png)

### Creating Users
Obviously there will be users to assign our roles to, and we can either create them manually for testing or setup a self user registration form that can send the new user information a to **Keycloak Endpoint**. For the sake of simplicity we'll be creating our users manually for now.

We can create users by navigating to **Users** in the left sidebar and "Create new user" and fill out fields with the user information. Once done navigate to the **Credentials** tab and "Set password" and in there its <u>important</u> that you turn off the "Temporary" tick. Lastly navigate to the **Role Mappings** tab and assign the desired roles to each user.

I created 3 users which informations are laid out like this :

| Username         | User1            | User2                                            | GlobalAdmin     |
|:---------------- |:---------------- |:------------------------------------------------ |:--------------- |
| Email            | user1@gmail.com  | user2@gmail.com                                  | admin@gmail.com |
| Email Verified   | Yes              | Yes                                              | Yes             |
| First Name       | User             | User                                             | Admin           |
| Last Name        | First            | Second                                           | Global          |
| Required Actions | none             | none                                             | none            |
| Password         | 12345            | 54321                                            | admin           |
| Roles            | `web-app-1` user | <div>`web-app-1` user<br>`web-app-1` admin</div> | GLOBAL_ADMIN    |

And we're all setup!

## Exploring Keycloak Endpoints
Some more advanced aspects of Keycloak are the various endpoints that it offers.