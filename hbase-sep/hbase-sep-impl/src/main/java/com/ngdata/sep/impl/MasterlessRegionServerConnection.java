/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ngdata.sep.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Append;
import org.apache.hadoop.hbase.client.BufferedMutator;
import org.apache.hadoop.hbase.client.BufferedMutatorParams;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Durability;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Increment;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.RegionLocator;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Row;
import org.apache.hadoop.hbase.client.RowMutations;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.client.TableBuilder;
import org.apache.hadoop.hbase.client.TableDescriptor;
import org.apache.hadoop.hbase.client.coprocessor.Batch;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.ipc.CoprocessorRpcChannel;
import org.apache.hadoop.hbase.security.User;

import com.google.protobuf.Descriptors;
import com.google.protobuf.Message;
import com.google.protobuf.Service;
import com.google.protobuf.ServiceException; 


/**
 * The hbase-indexer catches the replication stream in replicateBatch() 
 * so it can insert into an index or similar.
 */ 
abstract class MasterlessRegionServerConnection implements Connection { 
  
  private final Configuration conf; 
  private final User user; 
  private final ExecutorService pool; 
  private volatile boolean closed = false;

  public MasterlessRegionServerConnection(Configuration conf, ExecutorService pool, User user) throws IOException {
    this.conf = conf; 
    this.user = user; 
    this.pool = pool; 
  }
  
  /** Implement this method for custom behaviour */
  abstract protected void replicateBatch(List<? extends Row> actions, Object[] results, TableName tableName) 
      throws IOException, InterruptedException;
  
  /** Default implementation simply returns true */
  protected boolean isRelevantTable(TableName tableName) {
    return true;
  }
  
  @Override 
  public void abort(String why, Throwable e) {} 

  @Override 
  public boolean isAborted() { 
    return false; 
  } 

  @Override 
  public Configuration getConfiguration() { 
    return this.conf; 
  } 

  @Override 
  public BufferedMutator getBufferedMutator(TableName tableName) throws IOException { 
    return null; 
  } 

  @Override 
  public BufferedMutator getBufferedMutator(BufferedMutatorParams params) throws IOException { 
    return null; 
  } 

  @Override 
  public RegionLocator getRegionLocator(TableName tableName) throws IOException { 
    return null; 
  } 

  @Override 
  public Admin getAdmin() throws IOException { 
    return null; 
  } 

  @Override 
  public void close() throws IOException { 
    if (!this.closed) this.closed = true; 
  } 

  @Override 
  public boolean isClosed() { 
    return this.closed; 
  } 

  @Override 
  public TableBuilder getTableBuilder(final TableName tn, ExecutorService pool) { 
    if (isClosed()) { 
      throw new RuntimeException("IndexerConnection is closed."); 
    } 
    final Configuration passedInConfiguration = getConfiguration();
    if (!isRelevantTable(tn)) {
      return new NoopTableBuilder(passedInConfiguration, tn);
    }
    
    return new TableBuilder() { 
      @Override 
      public TableBuilder setOperationTimeout(int timeout) { 
        return null; 
      } 

      @Override 
      public TableBuilder setRpcTimeout(int timeout) { 
        return null; 
      } 

      @Override 
      public TableBuilder setReadRpcTimeout(int timeout) { 
        return null; 
      } 

      @Override 
      public TableBuilder setWriteRpcTimeout(int timeout) { 
        return null; 
      } 

      @Override 
      public Table build() { 
        return new Table() { 
          private final Configuration conf = passedInConfiguration; 
          private final TableName tableName = tn; 

          @Override 
          public TableName getName() { 
            return this.tableName; 
          } 

          @Override 
          public Configuration getConfiguration() { 
            return this.conf; 
          } 

          @Override 
          public void batch(List<? extends Row> actions, Object[] results) 
          throws IOException, InterruptedException { 
            MasterlessRegionServerConnection.this.replicateBatch(actions, results, tableName);
          }
          
          @Override 
          public HTableDescriptor getTableDescriptor() throws IOException { 
            return null; 
          } 

          @Override 
          public TableDescriptor getDescriptor() throws IOException { 
            return null; 
          } 

          @Override 
          public boolean exists(Get get) throws IOException { 
            return false; 
          } 

          @Override 
          public boolean[] exists(List<Get> gets) throws IOException { 
            return new boolean[0]; 
          } 

          @Override 
          public boolean[] existsAll(List<Get> gets) throws IOException { 
            return new boolean[0]; 
          } 

          @Override 
          public <R> void batchCallback(List<? extends Row> actions, Object[] results, Batch.Callback<R> callback) throws IOException, InterruptedException { 

          } 

          @Override 
          public Result get(Get get) throws IOException { 
            return null; 
          } 

          @Override 
          public Result[] get(List<Get> gets) throws IOException { 
            return new Result[0]; 
          } 

          @Override 
          public ResultScanner getScanner(Scan scan) throws IOException { 
            return null; 
          } 

          @Override 
          public ResultScanner getScanner(byte[] family) throws IOException { 
            return null; 
          } 

          @Override 
          public ResultScanner getScanner(byte[] family, byte[] qualifier) throws IOException { 
            return null; 
          } 

          @Override 
          public void put(Put put) throws IOException { 

          } 

          @Override 
          public void put(List<Put> puts) throws IOException { 

          } 

          @Override 
          public boolean checkAndPut(byte[] row, byte[] family, byte[] qualifier, byte[] value, Put put) throws IOException { 
            return false; 
          } 

          @Override 
          public boolean checkAndPut(byte[] row, byte[] family, byte[] qualifier, CompareFilter.CompareOp compareOp, byte[] value, Put put) throws IOException { 
            return false; 
          } 

          @Override 
          public boolean checkAndPut(byte[] row, byte[] family, byte[] qualifier, CompareOperator op, byte[] value, Put put) throws IOException { 
            return false; 
          } 

          @Override 
          public void delete(Delete delete) throws IOException { 

          } 

          @Override 
          public void delete(List<Delete> deletes) throws IOException { 

          } 

          @Override 
          public boolean checkAndDelete(byte[] row, byte[] family, byte[] qualifier, byte[] value, Delete delete) throws IOException { 
            return false; 
          } 

          @Override 
          public boolean checkAndDelete(byte[] row, byte[] family, byte[] qualifier, CompareFilter.CompareOp compareOp, byte[] value, Delete delete) throws IOException { 
            return false; 
          } 

          @Override 
          public boolean checkAndDelete(byte[] row, byte[] family, byte[] qualifier, CompareOperator op, byte[] value, Delete delete) throws IOException { 
            return false; 
          } 

          @Override 
          public void mutateRow(RowMutations rm) throws IOException { 

          } 

          @Override 
          public Result append(Append append) throws IOException { 
            return null; 
          } 

          @Override 
          public Result increment(Increment increment) throws IOException { 
            return null; 
          } 

          @Override 
          public long incrementColumnValue(byte[] row, byte[] family, byte[] qualifier, long amount) throws IOException { 
            return 0; 
          } 

          @Override 
          public long incrementColumnValue(byte[] row, byte[] family, byte[] qualifier, long amount, Durability durability) throws IOException { 
            return 0; 
          } 

          @Override 
          public void close() throws IOException { 

          } 

          @Override 
          public CoprocessorRpcChannel coprocessorService(byte[] row) { 
            return null; 
          } 

          @Override 
          public <T extends Service, R> Map<byte[], R> coprocessorService(Class<T> service, byte[] startKey, byte[] endKey, Batch.Call<T, R> callable) throws ServiceException, Throwable { 
            return null; 
          } 

          @Override 
          public <T extends Service, R> void coprocessorService(Class<T> service, byte[] startKey, byte[] endKey, Batch.Call<T, R> callable, Batch.Callback<R> callback) throws ServiceException, Throwable { 

          } 

          @Override 
          public <R extends Message> Map<byte[], R> batchCoprocessorService(Descriptors.MethodDescriptor methodDescriptor, Message request, byte[] startKey, byte[] endKey, R responsePrototype) throws ServiceException, Throwable { 
            return null; 
          } 

          @Override 
          public <R extends Message> void batchCoprocessorService(Descriptors.MethodDescriptor methodDescriptor, Message request, byte[] startKey, byte[] endKey, R responsePrototype, Batch.Callback<R> callback) throws ServiceException, Throwable { 

          } 

          @Override 
          public boolean checkAndMutate(byte[] row, byte[] family, byte[] qualifier, CompareFilter.CompareOp compareOp, byte[] value, RowMutations mutation) throws IOException { 
            return false; 
          } 

          @Override 
          public boolean checkAndMutate(byte[] row, byte[] family, byte[] qualifier, CompareOperator op, byte[] value, RowMutations mutation) throws IOException { 
            return false; 
          } 

          @Override
          public CheckAndMutateBuilder checkAndMutate(byte[] arg0, byte[] arg1) {
            return null;
          } 
          
          @Override 
          public void setOperationTimeout(int operationTimeout) { 

          } 

          @Override 
          public long getOperationTimeout(TimeUnit unit) { 
            return 0; 
          } 

          @Override 
          public int getOperationTimeout() { 
            return 0; 
          } 

          @Override 
          public long getRpcTimeout(TimeUnit unit) { 
            return 0; 
          } 

          @Override 
          public int getRpcTimeout() { 
            return 0; 
          } 

          @Override 
          public void setRpcTimeout(int rpcTimeout) { 

          } 

          @Override 
          public long getReadRpcTimeout(TimeUnit unit) { 
            return 0; 
          } 

          @Override 
          public int getReadRpcTimeout() { 
            return 0; 
          } 

          @Override 
          public void setReadRpcTimeout(int readRpcTimeout) { 

          } 

          @Override 
          public long getWriteRpcTimeout(TimeUnit unit) { 
            return 0; 
          } 

          @Override 
          public int getWriteRpcTimeout() { 
            return 0; 
          } 

          @Override 
          public void setWriteRpcTimeout(int writeRpcTimeout) { 

          }

        }; 
      } 
    }; 
  } 
    
} 