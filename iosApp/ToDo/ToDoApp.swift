import SwiftUI
import Shared

@main
struct ToDoApp : App {
    
    let repository: ToDoRepository = DependenciesIosKt.createRepository()
    
    @State
    var toDos: [ToDo] = []
    
    var body: some Scene {
        WindowGroup {
            ToDoList(
                toDos: toDos,
                onCreateItem: { content in Task { try await repository.add(content: content) } },
                onCheckedClick: { toDo in Task { try await repository.toggleComplete(toDo: toDo.toDataClass() ) } },
                onDeleteClick: { toDo in Task { try await repository.remove(toDo: toDo.toDataClass() ) } }
            ).task {
                for await toDos in repository.getList() {
                    self.toDos = toDos.map { $0.toStruct() }
                }
            }
        }
    }
}
