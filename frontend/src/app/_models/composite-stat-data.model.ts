export class CompositeStatDataModel {

    locationId: string;
    activeApplicationsData: ActiveApplicationsData;
    monthlyScreeningsData: MonthlyScreeningsModel[];
    screeningsStatData: ScreeningStatDataModel[];
    testsStatData: TestsStatData[];

}

export class ActiveApplicationsData {

    locationId: string;
    active: number;
    inactive: number;

}

export class MonthlyScreeningsModel {

    month: string;
    location: string;
    finalResultY: number;
    finalResultN: number;
}

export class ScreeningStatDataModel {

    day: number;
    location: string;
    countInvited: number;
    countScheduleSignedBack: number;
    countBeenToScreening: number;
    countFinalResultY: number;
    countFinalResultN: number;

}

export class TestsStatData {

    day: number;
    location: string;
    test: string;
    countStarted: number;
    countSuccess: number;

}