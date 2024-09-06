import Foundation
import Shared

struct ToDo {
    let id: Int64
    let content: String
    let complete: Bool
}

extension Shared.ToDo {
    func toStruct() -> ToDo {
        return ToDo(id: id, content: content, complete: complete)
    }
}

extension ToDo {
    func toDataClass() -> Shared.ToDo {
        return Shared.ToDo(id: id, content: content, complete: complete)
    }
}
