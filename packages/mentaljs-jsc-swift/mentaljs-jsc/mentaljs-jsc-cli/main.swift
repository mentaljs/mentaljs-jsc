//
//  main.swift
//  mentaljs-jsc-cli
//
//  Created by Steve Kite on 1/30/19.
//

import Foundation
import JavaScriptCore
import MentalJS

@objc protocol ConsoleExport: JSExport {
    func log(_ src: String)
}

@objc class ConsoleModule: NSObject, MentalModule, ConsoleExport {
    
    func register(context: JSContext) {
        context.setObject(self, forKeyedSubscript: "console" as NSCopying & NSObjectProtocol)
    }
    
    func log(_ src: String) {
        // print("CONSOLE: " + src)
    }
}

let console = ConsoleModule()

let start = NSDate()
let engine = MentalJSEngine(source: "function a() {}; for (var i = 0; i<1000000000;i++) { a('Hello') }", modules: [console])
let end = NSDate()
let timeInterval: Double = end.timeIntervalSince(start as Date)
print("Benchmark: \(timeInterval) seconds\n")
print("Benchmark: \(1000000/timeInterval) ops/sec\n")
