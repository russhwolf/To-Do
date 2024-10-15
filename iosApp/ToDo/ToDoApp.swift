import SwiftUI
import Shared

@main
struct ToDoApp : App {
    
    let repository: ToDoRepository = DependenciesIosKt.createRepository()
    
    @State
    var toDos: [ToDo] = []
    
    var body: some Scene {
        WindowGroup {
            Observing(repository.getList(), initialContent: { ProgressView() }) { toDos in
                ToDoList(
                    toDos: toDos.map { $0.toStruct() },
                    onCreateItem: { content in Task { try await repository.add(content: content) } },
                    onCheckedClick: { toDo in Task { try await repository.toggleComplete(toDo: toDo.toDataClass() ) } },
                    onDeleteClick: { toDo in Task { try await repository.remove(toDo: toDo.toDataClass() ) } }
                )
            }
        }
    }
}
