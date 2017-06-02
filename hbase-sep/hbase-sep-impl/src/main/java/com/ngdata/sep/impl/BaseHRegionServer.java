/*
 * Copyright 2012 NGDATA nv
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
package com.ngdata.sep.impl;

import org.apache.hadoop.hbase.shaded.com.google.protobuf.RpcController;
import org.apache.hadoop.hbase.shaded.com.google.protobuf.ServiceException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Server;
import org.apache.hadoop.hbase.ServerName;
import org.apache.hadoop.hbase.shaded.protobuf.generated.AdminProtos;
import org.apache.hadoop.hbase.shaded.protobuf.generated.QuotaProtos;
import org.apache.hadoop.hbase.zookeeper.ZooKeeperWatcher;

/**
 *
 */
public class BaseHRegionServer implements AdminProtos.AdminService.BlockingInterface, Server, org.apache.hadoop.hbase.ipc.PriorityFunction {

    @Override
    public AdminProtos.GetRegionInfoResponse getRegionInfo(RpcController rpcController, AdminProtos.GetRegionInfoRequest getRegionInfoRequest) throws ServiceException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public AdminProtos.GetStoreFileResponse getStoreFile(RpcController rpcController, AdminProtos.GetStoreFileRequest getStoreFileRequest) throws ServiceException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public AdminProtos.GetOnlineRegionResponse getOnlineRegion(RpcController rpcController, AdminProtos.GetOnlineRegionRequest getOnlineRegionRequest) throws ServiceException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public AdminProtos.OpenRegionResponse openRegion(RpcController rpcController, AdminProtos.OpenRegionRequest openRegionRequest) throws ServiceException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public AdminProtos.CloseRegionResponse closeRegion(RpcController rpcController, AdminProtos.CloseRegionRequest closeRegionRequest) throws ServiceException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public AdminProtos.UpdateFavoredNodesResponse updateFavoredNodes(RpcController controller, AdminProtos.UpdateFavoredNodesRequest request) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public AdminProtos.FlushRegionResponse flushRegion(RpcController rpcController, AdminProtos.FlushRegionRequest flushRegionRequest) throws ServiceException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public AdminProtos.SplitRegionResponse splitRegion(RpcController rpcController, AdminProtos.SplitRegionRequest splitRegionRequest) throws ServiceException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public AdminProtos.CompactRegionResponse compactRegion(RpcController rpcController, AdminProtos.CompactRegionRequest compactRegionRequest) throws ServiceException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public AdminProtos.ReplicateWALEntryResponse replicateWALEntry(RpcController rpcController, AdminProtos.ReplicateWALEntryRequest replicateWALEntryRequest) throws ServiceException {
        throw new UnsupportedOperationException("Not implemented");
    }


    @Override
    public AdminProtos.ReplicateWALEntryResponse replay(final RpcController controller, final AdminProtos.ReplicateWALEntryRequest request) throws ServiceException {
        throw new UnsupportedOperationException("Not implemented");
    }
  /*
  @Override
  public org.apache.hadoop.hbase.protobuf.generated.ClientProtos.MultiResponse replay(
          com.google.protobuf.RpcController controller,
          org.apache.hadoop.hbase.protobuf.generated.ClientProtos.MultiRequest request) {
    throw new UnsupportedOperationException("Not implemented");
  }
  */

    @Override
    public AdminProtos.RollWALWriterResponse rollWALWriter(RpcController rpcController, AdminProtos.RollWALWriterRequest rollWALWriterRequest) throws ServiceException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public AdminProtos.GetServerInfoResponse getServerInfo(RpcController rpcController, AdminProtos.GetServerInfoRequest getServerInfoRequest) throws ServiceException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public AdminProtos.StopServerResponse stopServer(RpcController rpcController, AdminProtos.StopServerRequest stopServerRequest) throws ServiceException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Configuration getConfiguration() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public ZooKeeperWatcher getZooKeeper() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public ServerName getServerName() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void abort(String s, Throwable throwable) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public boolean isAborted() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void stop(String s) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public boolean isStopped() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public int getPriority(org.apache.hadoop.hbase.shaded.protobuf.generated.RPCProtos.RequestHeader header, org.apache.hadoop.hbase.shaded.com.google.protobuf.Message param, org.apache.hadoop.hbase.security.User user) {
        return org.apache.hadoop.hbase.HConstants.NORMAL_QOS;
    }

    @Override
    public long getDeadline(org.apache.hadoop.hbase.shaded.protobuf.generated.RPCProtos.RequestHeader header, org.apache.hadoop.hbase.shaded.com.google.protobuf.Message param) {
        return 0;
    }

    @Override
    public org.apache.hadoop.hbase.client.ClusterConnection getConnection() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public org.apache.hadoop.hbase.client.ClusterConnection getClusterConnection() {
       throw new UnsupportedOperationException("Not implemented");
    }
    
    @Override
    public org.apache.hadoop.hbase.zookeeper.MetaTableLocator getMetaTableLocator() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public org.apache.hadoop.hbase.CoordinatedStateManager getCoordinatedStateManager() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public org.apache.hadoop.hbase.ChoreService getChoreService() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public AdminProtos.UpdateConfigurationResponse updateConfiguration(
            RpcController controller,
            AdminProtos.UpdateConfigurationRequest request)
            throws ServiceException {
        throw new UnsupportedOperationException("Not implemented");
    }
  
    @Override
    public AdminProtos.WarmupRegionResponse warmupRegion(
            RpcController controller,
            AdminProtos.WarmupRegionRequest request)
            throws ServiceException {
       throw new UnsupportedOperationException("Not implemented");
    }
    
    @Override
    public AdminProtos.GetRegionLoadResponse getRegionLoad(
            RpcController controller,
            AdminProtos.GetRegionLoadRequest request) 
            throws ServiceException {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    @Override
    public AdminProtos.CloseRegionForSplitOrMergeResponse closeRegionForSplitOrMerge(
            RpcController controller,
            AdminProtos.CloseRegionForSplitOrMergeRequest request) 
            throws ServiceException {
      throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public AdminProtos.ClearCompactionQueuesResponse clearCompactionQueues(RpcController controller,
	    AdminProtos.ClearCompactionQueuesRequest request) throws ServiceException {
      throw new UnsupportedOperationException("Not implemented");      
    }

    @Override
    public QuotaProtos.GetSpaceQuotaSnapshotsResponse getSpaceQuotaSnapshots(RpcController controller,
        QuotaProtos.GetSpaceQuotaSnapshotsRequest request) throws ServiceException {
        throw new UnsupportedOperationException("Not implemented");
    }
    
}
