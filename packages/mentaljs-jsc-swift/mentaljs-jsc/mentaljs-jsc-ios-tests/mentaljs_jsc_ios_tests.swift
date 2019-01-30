//
//  mentaljs_jsc_ios_tests.swift
//  mentaljs-jsc-ios-tests
//
//  Created by Steve Kite on 1/31/19.
//

import XCTest
import JavaScriptCore
import MentalJS

class mentaljs_jsc_ios_tests: XCTestCase {

    override func setUp() {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDown() {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

//    func testExample() {
//        // This is an example of a functional test case.
//        // Use XCTAssert and related functions to verify your tests produce the correct results.
//    }

    func testPerformanceExample() {
        // This is an example of a performance test case.
        self.measure {
            // let start = NSDate()
            let engine = MentalJSEngine(source: "function a() {}; for (var i = 0; i<1000000000;i++) { a('Hello') }", modules: [])
            // let end = NSDate()
            // let timeInterval: Double = end.timeIntervalSince(start as Date)
//            print("Benchmark: \(timeInterval) seconds\n")
//            print("Benchmark: \(1000000/timeInterval) ops/sec\n")
        }
    }

}
