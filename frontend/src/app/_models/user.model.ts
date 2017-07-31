import {Results} from "./results.model";
import {ScreeningInfo} from "./screeninginfo.model"
import {ScreeningStep} from "./screeningStep.model";
export class User {

    givenName: string;
    familyName: string;
    dateOfBirth: number;
    adminId: number;

    // from application info
    timesApplied: number;
    location: string;
    processStartedAt: number;

    testResults: Results[]=[];

    // from screening info
    screeningPersonalTime: string;

    screeningInfo: ScreeningInfo;

    screeningStep: ScreeningStep;

}
