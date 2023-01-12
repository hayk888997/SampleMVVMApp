# SampleMVVMApp

This is sample application which goal is to fetch hardcoded number of news from TheGuardian api and show to the user. There is also news details screen
for showing more info on the selected news, where webView was used to load url of that specific news item.


What to 

Expect
* MVVM design pattern implementation 
* Retrofit, okHTTP3 and interceptors usage
* Usage of several libraries from Jetpack suit
* 1 Unit test for AuthInterceptor
* 1 Functional test for viewModel. Testing init method and successful scenario only. 

Do not expect
* Local repository - means there is no data stored locally, if there is no internet connection no news will be shown
* UI solutions - Application is simple enough with 2 pages only, from UI there is just recyclerView, sharedElement transition and webView is used on the second page for not having empty screen only
* Pagination - Can be added easly using paging library from Jetpack if required
