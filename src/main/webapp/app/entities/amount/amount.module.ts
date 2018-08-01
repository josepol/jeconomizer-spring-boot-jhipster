import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JeconomizerSharedModule } from '../../shared';
import { JeconomizerAdminModule } from '../../admin/admin.module';
import {
    AmountService,
    AmountPopupService,
    AmountComponent,
    AmountDetailComponent,
    AmountDialogComponent,
    AmountPopupComponent,
    AmountDeletePopupComponent,
    AmountDeleteDialogComponent,
    amountRoute,
    amountPopupRoute,
} from './';

const ENTITY_STATES = [
    ...amountRoute,
    ...amountPopupRoute,
];

@NgModule({
    imports: [
        JeconomizerSharedModule,
        JeconomizerAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AmountComponent,
        AmountDetailComponent,
        AmountDialogComponent,
        AmountDeleteDialogComponent,
        AmountPopupComponent,
        AmountDeletePopupComponent,
    ],
    entryComponents: [
        AmountComponent,
        AmountDialogComponent,
        AmountPopupComponent,
        AmountDeleteDialogComponent,
        AmountDeletePopupComponent,
    ],
    providers: [
        AmountService,
        AmountPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JeconomizerAmountModule {}
