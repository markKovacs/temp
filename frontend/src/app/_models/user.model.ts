import {TestResult} from "./test-result.model";
export class User {

    givenName: string;
    familyName: string;
    dateOfBirth: number;
    adminId: number;

    // from application info
    timesApplied: number;
    location: string;
    processStartedAt: number;

    testResults: TestResult;

    // from screening info
    screeningDay: string;
    screeningGroupTime: string;
    screeningPersonalTime: string;
    scheduleSignedBack: boolean;

}
