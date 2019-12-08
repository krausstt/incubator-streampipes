/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

import { NgModule } from '@angular/core';
import { HomeComponent } from './home.component';
import { HomeService } from './home.service';
import { MatGridListModule, MatIconModule } from '@angular/material';
import { FlexLayoutModule } from '@angular/flex-layout';
import { CommonModule } from '@angular/common';
import { StatusComponent } from "./components/status.component";
import { RestApi } from "../services/rest-api.service";

@NgModule({
    imports: [
        CommonModule,
        FlexLayoutModule,
        MatGridListModule,
        MatIconModule
    ],
    declarations: [
        HomeComponent,
        StatusComponent
    ],
    providers: [
        HomeService,
        RestApi,
        {
            provide: '$http',
            useFactory: ($injector: any) => $injector.get('$http'),
            deps: ['$injector'],
        },
        {
            provide: 'apiConstants',
            useFactory: ($injector: any) => $injector.get('apiConstants'),
            deps: ['$injector'],
        },
        {
            provide: 'AuthStatusService',
            useFactory: ($injector: any) => $injector.get('AuthStatusService'),
            deps: ['$injector'],
        },
    ],
    entryComponents: [
        HomeComponent
    ]
})
export class HomeModule {
}