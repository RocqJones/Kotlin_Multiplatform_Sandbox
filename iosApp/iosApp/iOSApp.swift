import SwiftUI

@main
struct iOSApp: App {
	var body: some Scene {
		WindowGroup {
            // ViewModel has a greetings property that is an array of String phrases. SwiftUI connects the view model (ContentView.ViewModel) with the view (ContentView).
            ContentView(viewModel : ContentView.ViewModel())
		}
	}
}
