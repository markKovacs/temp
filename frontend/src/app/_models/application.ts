import {Results} from "./results.model";
import {UserScreeningDisplayModel} from "./screening/user-screening-display.model";

export class Application {
    id: string;
    comment: string;
    processStartedAt: number;

    testResults: Results[]= [];

    // from screening info
    screeningGroupTime: Date;
    screeningPersonalTime: Date;
    scheduleSignedBack: boolean;

    screeningSteps: UserScreeningDisplayModel[];

    active: boolean;
    finalResult: boolean;
}