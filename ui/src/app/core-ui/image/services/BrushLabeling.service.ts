/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import { Injectable } from '@angular/core';
import Konva from 'konva';
import { Annotation } from '../../../core-model/coco/Annotation';
import { ICoordinates } from '../model/coordinates';
import { ColorService } from './color.service';

@Injectable()
export class BrushLabelingService {

  private points: number[];
  private brushsize: number;

  private isLabeling: boolean;

  constructor(private colorService: ColorService) {

  }


  startLabeling(position: ICoordinates, brushSize: number) {
    this.isLabeling = true;
    this.points = [];
    this.points.push(position.x);
    this.points.push(position.y);
    this.brushsize = brushSize;
  }

  executeLabeling(position: ICoordinates) {
    this.points.push(position.x);
    this.points.push(position.y);
  }

  endLabeling(position: ICoordinates) {
    this.isLabeling = false;
    return [this.points, this.brushsize];
  }

  tempDraw(layer: Konva.Layer, shift: ICoordinates, label: string) {
    if (this.isLabeling) {
      const tmp = [];
      for (let i = 0; i < this.points.length; i += 2) {
        tmp.push(this.points[i] + shift.x);
        tmp.push(this.points[i + 1] + shift.y);
      }

      const line = new Konva.Line({
        points: tmp,
        stroke: this.colorService.getColor(label),
        opacity: 0.8,
        strokeWidth: this.brushsize,
        closed: false
      });
      layer.add(line);
    }
  }

  draw(layer: Konva.Layer, shift: ICoordinates, annotation: Annotation, imageView) {
    const tmp = [];
    for (let i = 0; i < annotation.segmentation[0].length; i += 2) {
      tmp.push(annotation.segmentation[0][i] + shift.x);
      tmp.push(annotation.segmentation[0][i + 1] + shift.y);
    }

    const line = new Konva.Line({
      points: tmp,
      stroke: this.colorService.getColor(annotation.category_name),
      opacity: 0.5,
      strokeWidth: this.brushsize,
      closed: false,
      draggable: false,
    });
    layer.add(line);

    const transformer = new Konva.Transformer({
      anchorFill: '#FFFFFF',
      anchorSize: 10,
      rotateEnabled: false,
      enabledAnchors: [],
      borderStroke: 'green',
    });

    if (annotation.isSelected) {
      transformer.attachTo(line);
    }

    this.addMouseHandler(line, annotation, layer, transformer);
    this.addClickHandler(line, annotation, layer, transformer);

    layer.add(line);
    layer.add(transformer);
  }

  private addClickHandler(rect, annotation, layer, transformer) {
    rect.on('click', function() {
      annotation.isSelected = true;
      transformer.attachTo(this);
      layer.batchDraw();
    });

    rect.on('dblclick', function() {
      annotation.isSelected = false;
      transformer.detach();
      layer.batchDraw();
    });

  }

  private addMouseHandler(rect, annotation, layer, transformer) {
    rect.on('mouseover', function() {
      annotation.isHovered = true;
      rect.opacity(0.8);
      layer.batchDraw();
    });

    rect.on('mouseout', function() {
      annotation.isHovered = false;
      rect.opacity(0.5);
      layer.batchDraw();
    });

    transformer.on('mouseover', function() {
      annotation.isHovered = true;
    });

    transformer.on('mouseout', function() {
      annotation.isHovered = false;
    });
  }

}