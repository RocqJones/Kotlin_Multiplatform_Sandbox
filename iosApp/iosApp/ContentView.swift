import SwiftUI
import shared

struct ContentView: View {
    // ViewModel class for ContentView, which will prepare and manage data for it
    // The @ObservedObject property wrapper is used to subscribe to the view model.
    @ObservedObject private(set) var viewModel : ViewModel
    
    // let in Swift is similar to Kotlin's val
	//let phrases = Greeting().greet()

	var body: some View {
        // The List function produces a list of Text items.
        //List(phrases, id: \.self) { Text($0) }
        
        ListView(phrases : viewModel.greetings).onAppear {
            self.viewModel.startObserving()
        }
	}
}

extension ContentView {
    // ContentView.ViewModel is declared as an ObservableObject.
    // @MainActor annotation. The annotation ensures that all asynchronous operations within ViewModel run on the main thread to comply with the Kotlin/Native requirement:
    @MainActor
    class ViewModel: ObservableObject {
        @Published var greetings: Array<String> = []

        // Consume the flow using SKIE﻿
        // To support concurrency, wrap the asynchronous operation in a Task
        func startObserving() {
            Task {
                for await phrase in Greeting().greet() {
                    self.greetings.append(phrase)
                }
            }
        }
    }
}

struct ListView: View {
    let phrases: Array<String>

    var body: some View {
        List(phrases, id: \.self) {
            Text($0)
        }
    }
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView(viewModel : ContentView.ViewModel())
	}
}
