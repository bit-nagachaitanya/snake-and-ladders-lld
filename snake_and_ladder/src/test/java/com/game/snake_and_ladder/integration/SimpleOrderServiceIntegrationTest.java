/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.game.snake_and_ladder.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import com.game.snake_and_ladder.transport.SimpleOrderServiceEndpoint;
import com.game.snake_and_ladder.transport.SimpleOrderServiceClient;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.game.snake_and_ladder.service.Confirmation;
import com.game.snake_and_ladder.service.Item;
import com.game.snake_and_ladder.service.Order;

/**
 * <code>SimpleOrderServiceIntegrationTest</code> runs as part of the Integration phase of the build and is
 * meant for end to end service testing.
 */
class SimpleOrderServiceIntegrationTest {

  private static SimpleOrderServiceEndpoint service;
  private static SimpleOrderServiceClient client;

  @Test
  void simpleRoundTripTest() throws Exception {
    Order simpleOrder = createOrder();
    Confirmation c = client.submitOrder(simpleOrder);

    assertEquals(c.getOrderId(), simpleOrder.getOrderId());
    assertEquals(c.getCustomerId(), simpleOrder.getCustomerId());
    assertTrue(c.getEstimatedCompletion() > 0);
  }

  @BeforeAll
  public static void setupTransport() throws Exception {
    InetSocketAddress endpointAddress = new InetSocketAddress("0.0.0.0", 12345);
    service = new SimpleOrderServiceEndpoint(endpointAddress);
    client = new SimpleOrderServiceClient(endpointAddress);

    service.start();
    client.start();
  }

  @AfterAll
  public static void shutdownTransport() throws Exception {
    client.stop();
    service.stop();
  }

  public Order createOrder() {
    return Order.newBuilder().setOrderId(1).setCustomerId(1).setOrderItems(createItems()).build();
  }

  public List<Item> createItems() {
    List<Item> items = new ArrayList<Item>();
    for (int x = 0; x < 5; x++)
      items.add(Item.newBuilder().setName("Item-" + x).setQuantity(x + 1).setSku(1230 + x).build());
    return items;
  }

}
