import Foundation
import Shared

struct ToDo {
    let id: Int64
    let content: String
    let complete: Bool
}

extension Shared.db.ToDo {
    func toStruct() -> ToDo {
        return ToDo(id: id, content: content, complete: complete)
    }
}

extension ToDo {
    func toDataClass() -> Shared.db.ToDo {
        return Shared.db.ToDo(id: id, content: content, complete: complete)
    }
}
