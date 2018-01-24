import {Component} from "@angular/core";
import {StatDataService} from "../../_services/statdata.service";
import {CompositeStatDataModel, ScreeningStatDataModel, TestsStatData} from "../../_models/composite-stat-data.model";
import {isNullOrUndefined} from "util";

@Component({
    moduleId: module.id,
    templateUrl: './marketing-data.component.html',
    styleUrls: ['./marketing-data.component.css']
})
export class MarketingDataPageComponent {

    data: CompositeStatDataModel;
    testDisplayData: TestsStatData[];
    screeningStatData: ScreeningStatDataModel[];

    inQueue: ScreeningStatDataModel;

    today = new Date();

    fromDateTest: Date;
    toDateTest: Date;

    fromDateScr: Date;
    toDateScr: Date;

    testCountsStarted = {};
    testCountSuccess = {};

    scrCounts = {};

    en = {
        firstDayOfWeek: 1,
        dayNames: ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"],
        dayNamesShort: ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"],
        dayNamesMin: ["Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"],
        monthNames: ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"],
        monthNamesShort: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
        today: 'Today',
        clear: 'Clear'
    };

    constructor(private statDataService: StatDataService) {
        this.toDateTest = new Date();
        this.toDateScr = new Date();
        const location = JSON.parse(localStorage.getItem('chosenLocation')).id;
        this.statDataService.getStatistics(location).subscribe(
            (data: CompositeStatDataModel) => {
                this.data = data;
                this.testDisplayData = data.testsStatData;
                this.screeningStatData = data.screeningsStatData.filter(value => !isNullOrUndefined(value.day));
                this.inQueue = data.screeningsStatData.filter(value => isNullOrUndefined(value.day))[0];
                this.countTest(this.testDisplayData);
                this.countScr(this.screeningStatData);
            },
            error2 => console.log(error2)
        )
    }

    filterDateTest() {
        this.testDisplayData = this.data.testsStatData;
        this.testDisplayData = this.testDisplayData.filter(
            value => new Date(value.day).getTime() > this.fromDateTest.getTime()
                && new Date(value.day).getTime() < this.toDateTest.getTime());

        this.countTest(this.testDisplayData);

    }

    filterDateScr() {
        this.screeningStatData = this.data.screeningsStatData.filter(value => !isNullOrUndefined(value.day));
        this.screeningStatData = this.screeningStatData.filter(
            value => new Date(value.day).getTime() > this.fromDateScr.getTime()
                && new Date(value.day).getTime() < this.toDateScr.getTime());

        this.countScr(this.screeningStatData);

    }


    countTest(array){

        this.testCountsStarted = {};
        this.testCountSuccess = {};

        array.forEach(value => {
            if (this.testCountsStarted[value.test] === undefined) {
                this.testCountsStarted[value.test] = 0;
            }
            if (this.testCountSuccess[value.test] === undefined) {
                this.testCountSuccess[value.test] = 0;
            }
            this.testCountsStarted[value.test] += value.countStarted;
            this.testCountSuccess[value.test] += value.countSuccess;

        });

    }

    countScr(array){

        this.scrCounts = {
            "Total": 0,
            "Signed back": 0,
            "Final result: Y": 0,
            "Final result: N": 0
        };

        array.forEach(value => {
            this.scrCounts["Total"] += value.total;
            this.scrCounts["Signed back"] += value.scheduleSignedBack;
            this.scrCounts["Final result: Y"] += value.finalResultY;
            this.scrCounts["Final result: N"] += value.finalResultN;

        });
    }

    resetTest(){
        this.fromDateTest = null;
        this.testDisplayData = this.data.testsStatData;
        this.countTest(this.testDisplayData);
    }

    resetScr(){
        this.fromDateScr = null;
        this.screeningStatData = this.data.screeningsStatData;
        this.countScr(this.screeningStatData);
    }

}