import SwiftUI
import Shared

@main
struct ToDoApp : App {
        
    var body: some Scene {
        WindowGroup {
            let repository: ToDoRepositoryIos = DependenciesIosKt.createRespository()
            let publisher = createPublisher(repository.getList())
                .map { $0.items.map { $0.toStruct() } }
                .eraseToAnyPublisher()
            ToDoList(
                toDos: PublishedFlow(publisher, defaultValue: []),
                onCreateItem: { repository.add(content: $0) },
                onCheckedClick: { repository.toggleComplete(toDo: $0.toDataClass())},
                onDeleteClick: { repository.remove(toDo: $0.toDataClass()) }
            )
        }
    }
}
