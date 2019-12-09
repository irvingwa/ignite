/*
 * Copyright 2019 GridGain Systems, Inc. and Contributors.
 *
 * Licensed under the GridGain Community Edition License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.gridgain.com/products/software/community-edition/gridgain-community-edition-license
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.ignite.internal.processors.query.calcite.rule;

import org.apache.calcite.plan.Convention;
import org.apache.calcite.plan.RelTraitSet;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.convert.ConverterRule;
import org.apache.calcite.rel.logical.LogicalTableScan;
import org.apache.ignite.internal.processors.query.calcite.rel.IgniteConvention;
import org.apache.ignite.internal.processors.query.calcite.rel.IgniteTableScan;

/**
 *
 */
public class TableScanConverter extends ConverterRule {
    public TableScanConverter() {
        super(LogicalTableScan.class, Convention.NONE, IgniteConvention.INSTANCE, "TableScanConverter");
    }

    @Override public RelNode convert(RelNode rel) {
        LogicalTableScan scan = (LogicalTableScan) rel;

        RelTraitSet traitSet = scan.getTraitSet().replace(IgniteConvention.INSTANCE);
        return new IgniteTableScan(rel.getCluster(), traitSet, scan.getTable());
    }
}
