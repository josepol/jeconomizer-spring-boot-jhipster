import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JeconomizerMovementModule } from './movement/movement.module';
import { JeconomizerCategoryModule } from './category/category.module';
import { JeconomizerPercentageModule } from './percentage/percentage.module';
import { JeconomizerAmountModule } from './amount/amount.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        JeconomizerMovementModule,
        JeconomizerCategoryModule,
        JeconomizerPercentageModule,
        JeconomizerAmountModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JeconomizerEntityModule {}
