//
//  MentalJS.swift
//  mentaljs-jsc
//
//  Created by Steve Kite on 1/30/19.
//

import Foundation
import JavaScriptCore

public protocol MentalModule {
    func register(context: JSContext)
}

public class MentalJSEngine {
    
    private let context = JSContext()!
    
    public init(source: String, modules: Array<MentalModule>) {
        context.exceptionHandler = { context, exception in
            print("JS Error: \(exception)")
        }

        for m in modules {
            m.register(context: self.context)
        }
        let res = self.context.evaluateScript(source)
        print(res)
    }
}
