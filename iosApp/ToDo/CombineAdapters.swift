import Combine
import shared

// Thanks to Lamert Westerhoff who initially suggested the patterns behind createPublisher() and PublishedFlow to me

func createPublisher<T>(_ flowAdapter: FlowAdapter<T>) -> AnyPublisher<T, KotlinError> {
    return Deferred<Publishers.HandleEvents<PassthroughSubject<T, KotlinError>>> {
        let subject = PassthroughSubject<T, KotlinError>()
        let job = flowAdapter.subscribe { (item) in
            let _ = subject.send(item)
        } onError: { (error) in
            subject.send(completion: .failure(KotlinError(error)))
        } onComplete: {
            subject.send(completion: .finished)
        }
        return subject.handleEvents(receiveCancel: {
            job.cancel(cause: nil)
        })
    }.eraseToAnyPublisher()
}

class PublishedFlow<T> : ObservableObject {
    @Published
    var output: T
    
    init<E>(_ publisher: AnyPublisher<T, E>, defaultValue: T) {
        output = defaultValue
        
        publisher
            .replaceError(with: defaultValue)
            .compactMap { $0 }
            .receive(on: DispatchQueue.main)
            .assign(to: &$output)
    }
}

class KotlinError: LocalizedError {
    let throwable: KotlinThrowable
    init(_ throwable: KotlinThrowable) {
        self.throwable = throwable
    }
    var errorDescription: String? {
        get { throwable.message }
    }
}
