

I have used clean clean architecture with an MVVM pattern to refactor the app. 
 
- Data layer responsible for handling data flow coming from the local and remote data source, in case of our simple case, I considered news.json as a remote source of data, local data source handle room database operations, and as a single source of data to the app, repository class responsible of handling logic of saving data to or retrieving data from the database 

- Domain layer which composed of NewsUsecase, in our case it simple implementation just acts as a middle interface between presentation and data layer, in larger-scale projects. the use case would contain application business logic and ant data manipulations or transformations 

- presentation layers, and it's represented in the view model and news activity, view model hold live-data builder the build live data observed by the news activity. 
- 

- Things to be improved:
  add more UI testing. 
  add integration test.
























