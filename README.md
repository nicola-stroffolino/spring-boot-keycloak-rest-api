# Spring Boot REST API

## The Purpose of the Application
Configuring a Spring Boot REST API that utilizes Keycloak as an Authorization server. The general idea is utilizing Keycloak as a way to obtain Access, Refresh and ID tokens that can then be used to authorize request to protected content in our resource server.

## The Advantages of Keycloak
In the versions of Spring Boot 2.x and onwards the best way to integrate Keycloak in our application was with the use of the **Keycloak Client Adapter** library.

Unfortunately with the advent of Spring Boot 3 most of its classes and method became deprecated, but it's not so much of a problem, as Keycloak still provides a multitude of **endpoints** that can allow us to manually execute authentication tasks.

## Setting Up a Keycloak Server
To utilize the services that Keycloak provides we firstly have to start a server that for this example will be run locally on the port **8081**.

The server can either be downloaded from their [official website](https://www.keycloak.org/downloads) or in the releases of their [GitHub repository](https://github.com/keycloak/keycloak/releases). The version I ended up using was Keycloak 20.0.3, but it shouldn't matter too much as long as it is one of the latest releases.

Once the compressed file is downloaded you can just unzip it and run the `kc.bat` (or `kc.sh` for Mac users) situated in the **bin** folder. It is recommended to start the server via terminal as it allows you to specify some essential parameters such as the `http-port`.

My command would result : `bin/kc.sh start-dev --http-port=8081`

Now if we reach to the http://localhost:8081 we should be presented with this :

![Welcome to Keycloak](./img/Welcome%20to%20Keycloak.png)
