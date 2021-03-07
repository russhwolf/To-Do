import Foundation
import shared

struct ToDo {
    let id: Int64
    let content: String
    let complete: Bool
}

extension shared.ToDo {
    func toStruct() -> ToDo {
        return ToDo(id: id, content: content, complete: complete)
    }
}

extension ToDo {
    func toDataClass() -> shared.ToDo {
        return shared.ToDo(id: id, content: content, complete: complete)
    }
}

extension ToDo : Identifiable {
    typealias ID = Int64
}
