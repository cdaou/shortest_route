# shortest_route

This is a solution to the shortest route problem, using network routing algorithms in transport networks.

## Description
With the development of technology and the evolution of societies, more and more people are using a passenger car, which, combined with the urbanization of the modern times, results in traffic congestion and long delay in passenger movement, especially in large urban centers. Thus, in the context of Intelligent Transport Systems (ITS) and aiming at traffic decongestion, in this project a Java and JavaScript application has been developed, that implement network routing algorithms in transport networks to find the shortest route. More specifically, the communication technologies used in ITS, network routing algorithms and protocols were studied and the Dijkstra and Bellman-Ford algorithms were used to develop the application. The algorithms are applied to a graph representing a part of the road network, that is roads are considered as the links and lanterns as the nodes of the graph and the weight of the links indicates the traffic factor that has a road at that moment. The user can select the initial node in which is located, and the system calculates and displays the shortest path to certain specific destinations (e.g. airport, bus station, central points, etc.) defined by the administrator. Βoth projects were implemented for the area of the center of Thessaloniki.

## Desktop Application

This implementation is more useful to the system administrator. In this case the map is static and shows a specific area, which has been pre-selected by the program. Τhe nodes are inserted by the administrator in the constellation and coordinate files. The advantage of this application is that the administrator can very easily edit the graph and perform tests.

### How to run

* To run Bellman-Ford you will need JavaFX. You can find it [here](https://gluonhq.com/products/javafx/ "JavaFX").
  For Eclipse IDE you can just `Go to Help > Go to Eclipse MarketPlace > Search e(fx)clipse > Install it`. Then, to add it on the project just follow: `Java Build Path > Libraries > Add Libraries > JavaFx SDK`
* Download a project and import as an existing project into your IDE.
* Run Main class.

### User Interface

![picture](https://uc874c1176464acd059c15f13d46.previews.dropboxusercontent.com/p/thumb/AA1AazRm1vP212ihhEoTRGFUqM8Wrc53-3wRx0qmWLQUcMz2x3S4JzrZgizyGNyZPv7t0PbvsI9gdalxP_qwSU43HzF3yWvRRGchN91X6AnPXZ9X7P_U6GRd4fDtx3LIz1miGJQMXkJ-vznwWx2DQEiEPj-WOxEds6Ip9o12p2R_zIeQkunrios2waVop7XqoeYVHtPL5L4MF68tHzFy9pK7azKLhyCtd79yXez6lCNfMnuwRf_48UmRgfC7CuaP25dH1I4y57olohqQlonDWoQnpM1rf8T5H44BVy8QgbXq6zdqX4h_kUItR3rRjBk6Qqy33BroE-4Y4tnwBDhZFlSiaH5UymhzZWaMe4M-DmaTO916f0Djt0FsoMLOtIlWjoI28d-iBuyexf2CgXZHAPUH/p.jpeg?fv_content=true&size_mode=5)


