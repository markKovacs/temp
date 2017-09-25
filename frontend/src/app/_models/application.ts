import {Results} from "./results.model";
import {UserScreeningDisplayModel} from "./user-screening-display.model";

export class Application {

    comment: string;
    processStartedAt: number;

    testResults: Results[]= [];

    // from screening info
    screeningGroupTime: Date;
    screeningPersonalTime: Date;
    scheduleSignedBack: boolean;

    screeningSteps: UserScreeningDisplayModel[];

    finalResult: boolean;
}