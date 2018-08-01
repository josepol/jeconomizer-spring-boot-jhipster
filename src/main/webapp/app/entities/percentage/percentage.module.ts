import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JeconomizerSharedModule } from '../../shared';
import {
    PercentageService,
    PercentagePopupService,
    PercentageComponent,
    PercentageDetailComponent,
    PercentageDialogComponent,
    PercentagePopupComponent,
    PercentageDeletePopupComponent,
    PercentageDeleteDialogComponent,
    percentageRoute,
    percentagePopupRoute,
} from './';

const ENTITY_STATES = [
    ...percentageRoute,
    ...percentagePopupRoute,
];

@NgModule({
    imports: [
        JeconomizerSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PercentageComponent,
        PercentageDetailComponent,
        PercentageDialogComponent,
        PercentageDeleteDialogComponent,
        PercentagePopupComponent,
        PercentageDeletePopupComponent,
    ],
    entryComponents: [
        PercentageComponent,
        PercentageDialogComponent,
        PercentagePopupComponent,
        PercentageDeleteDialogComponent,
        PercentageDeletePopupComponent,
    ],
    providers: [
        PercentageService,
        PercentagePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JeconomizerPercentageModule {}
