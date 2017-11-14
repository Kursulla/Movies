# Movies ![Image of Yaktocat](https://www.bitrise.io/app/10e6c10d9838fe0e/status.svg?token=q4B5Nqvmkbkt090UcEdWvw&branch=develop)

Small app for consuming The Movie Database API.

## Libraries:
- Dagger 2
- Retrofit2
- RxJava2
- Glide
- Mockito
- Espresso

## Short description:

I like to separate application in a domain layers. That is why we have  "discovery" and "details" packages. 

Generally, data layer for each domain should be part of a domain.
But in this case, since data layer is simple and reusable, I decided to go with "hybrid" approach: to have data layer extracted and independent. 
In next iteration, we could extract it into the independent module and benefit from faster build time with multi-module architecture. 

In UI layer I applied MVP. There are two approaches when it comes to the View: one is to have Activity implements Mvp.View and another is to have Mvp.View implementation as an independent entity. 
I think the second approach gives me better separation of concerns and easier DI.

When it comes to DI I like to use old-school approach by having it done via c-tors. I use Dagger (or any other framework) only when c-tor DI is not possible or not readable.  
