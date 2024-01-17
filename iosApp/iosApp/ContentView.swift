import SwiftUI
import shared

struct ContentView: View {
    // let in Swift is similar to Kotlin's val
	let phrases = Greeting().greet()

	var body: some View {
        // The List function produces a list of Text items.
        List(phrases, id: \.self) {
            Text($0)
        }
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
