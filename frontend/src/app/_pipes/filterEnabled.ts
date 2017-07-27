import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'filterEnabled',
    pure: false
})
export class FilterEnabled implements PipeTransform {
    transform(items: any[]): any {
        if (!items) { return []; }
        return items.filter(item => item.enabled);
    }
}
