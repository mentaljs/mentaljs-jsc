//
//  ViewController.swift
//  mentaljs-jsc-ios
//
//  Created by Steve Kite on 1/31/19.
//

import UIKit
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


class ViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.

        let iterations = 1000000;
        let start = NSDate()
        let engine = MentalJSEngine(source: "function a() {}; for (var i = 0; i<" + String(iterations) + ";i++) { a('Hello') }", modules: [console])
        let end = NSDate()
        let timeInterval: Double = end.timeIntervalSince(start as Date)
        print("Benchmark: \(timeInterval) seconds\n")
        print("Benchmark: \(Double(iterations) / timeInterval) ops/sec\n")

    }


}

