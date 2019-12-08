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
package com.github.jqudt.onto.units;

import com.github.jqudt.Unit;
import com.github.jqudt.onto.UnitFactory;

public class ConcentrationUnit {

	public static Unit MOLE_PER_CUBIC_METER = UnitFactory.getInstance().getUnit("http://qudt.org/vocab/unit#MolePerCubicMeter");

	public static Unit MOLAR = UnitFactory.getInstance().getUnit("http://www.openphacts.org/units/Molar");
	public static Unit MILLIMOLAR = UnitFactory.getInstance().getUnit("http://www.openphacts.org/units/Millimolar");
	public static Unit NANOMOLAR = UnitFactory.getInstance().getUnit("http://www.openphacts.org/units/Nanomolar");
	public static Unit MICROMOLAR = UnitFactory.getInstance().getUnit("http://www.openphacts.org/units/Micromolar");

	public static Unit GRAM_PER_LITER = UnitFactory.getInstance().getUnit("http://www.openphacts.org/units/GramPerLiter");
	public static Unit MICROGRAM_PER_MILLILITER = UnitFactory.getInstance().getUnit("http://www.openphacts.org/units/MicrogramPerMilliliter");
	public static Unit PICOGRAM_PER_MILLILITER = UnitFactory.getInstance().getUnit("http://www.openphacts.org/units/PicogramPerMilliliter");

	private ConcentrationUnit() {}

}
