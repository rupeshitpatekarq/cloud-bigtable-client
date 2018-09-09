/*
 * Copyright 2018 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.cloud.bigtable.hbase2_x.adapters.admin;

import java.util.concurrent.CompletableFuture;

import org.apache.hadoop.hbase.TableName;

import com.google.bigtable.admin.v2.DropRowRangeRequest;
import com.google.cloud.bigtable.config.Logger;
import com.google.cloud.bigtable.grpc.BigtableInstanceName;
import com.google.cloud.bigtable.hbase2_x.BigtableTableAdminClient;

public class BigtableAdminAdaptor {
  private final Logger LOG = new Logger(getClass());
  private final BigtableTableAdminClient bigtableTableAdminClient;
  private final BigtableInstanceName bigtableInstanceName;

  public BigtableAdminAdaptor(BigtableTableAdminClient bigtableTableAdminClient,
      BigtableInstanceName bigtableInstanceName) {
    this.bigtableTableAdminClient = bigtableTableAdminClient;
    this.bigtableInstanceName = bigtableInstanceName;
  }

  public CompletableFuture<Void> truncateTableAsync(TableName tableName, boolean preserveSplits) {
    if (!preserveSplits) {
      LOG.info("truncate will preserveSplits. The passed in variable is ignored.");
    }
    DropRowRangeRequest request = DropRowRangeRequest.newBuilder().setDeleteAllDataFromTable(true)
        .setName(bigtableInstanceName.toTableNameStr(tableName.getNameAsString())).build();
    return bigtableTableAdminClient.dropRowRangeAsync(request).thenApply(r -> null);
  }
}
