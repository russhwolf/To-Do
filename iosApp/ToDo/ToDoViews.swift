import SwiftUI
import Combine

struct ToDoList : View {
    var toDos: [ToDo] // TODO can we make this not know about SKIE?

    let onCreateItem: (String) -> Void
    let onCheckedClick: (ToDo) -> Void
    let onDeleteClick: (ToDo) -> Void
    
    var body: some View {
        VStack {
            ToDoInput(onCreateItem: onCreateItem)
            ScrollView {
                LazyVStack {
                    ForEach(toDos, id: \.id) { toDo in
                        ToDoItem(
                            content: toDo.content,
                            checked: toDo.complete,
                            onCheckedClick: { onCheckedClick(toDo) },
                            onDeleteClick: { onDeleteClick(toDo) }
                        )
                    }
                }
                Spacer()
            }
        }.accentColor(Color(red: 0x37/255.0, green: 0x59/255.0, blue: 0xdf/255.0))
    }
}

struct ToDoInput : View {
    let onCreateItem: (String) -> Void
    
    @State
    var input = ""
    
    var body: some View {
        VStack {
            HStack {
                TextField("Description",text: $input)
                    .padding()
                Button("Create") {
                    if !input.isEmpty {
                        onCreateItem(input)
                        input = ""
                    }
                }.padding()
            }
            Divider()
        }
    }
}

struct ToDoItem : View {
    let content: String
    let checked: Bool
    
    let onCheckedClick: () -> Void
    let onDeleteClick: () -> Void
    
    var body: some View {
        VStack {
            HStack {
                Image(systemName: checked ? "checkmark.square.fill" : "square")
                    .padding()
                    .foregroundColor(checked ? Color(red: 0x37/255.0, green: 0x5A/255.0, blue: 0x86/255.0) : .primary)
                    .onTapGesture { onCheckedClick() }
                Text(content)
                Spacer()
                Image(systemName: "xmark")
                    .padding()
                    .foregroundColor(.red)
                    .onTapGesture { onDeleteClick() }
            }
            Divider()
        }
    }
}

struct ToDoList_Previews: PreviewProvider {

    static var previews: some View {
        var toDos = [
            ToDo(id: 0, content: "Make a list", complete: true),
            ToDo(id: 1, content: "Check it twice", complete: false)
        ]
        
        return Group {
            ToDoList(
                toDos: toDos,
                onCreateItem: { input in
                    toDos.append(ToDo(id: (toDos.last?.id ?? -1) + 1, content: input, complete: false))
                },
                onCheckedClick: { toDo in
                    if let updatedIndex = toDos.firstIndex(where: { $0.id == toDo.id }) {
                        toDos[updatedIndex] = ToDo(id: toDo.id, content: toDo.content, complete: !toDo.complete)
                    }
                },
                onDeleteClick: { toDo in
                    if let removedIndex = toDos.firstIndex(where: { $0.id == toDo.id }) {
                        toDos.remove(at: removedIndex)
                    }
                }
            )
        }
    }
}
