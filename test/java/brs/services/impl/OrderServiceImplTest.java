package brs.services.impl;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import brs.Order.Ask;
import brs.Order.Bid;
import brs.db.BurstIterator;
import brs.db.BurstKey;
import brs.db.BurstKey.LongKeyFactory;
import brs.db.VersionedEntityTable;
import brs.db.store.OrderStore;
import brs.services.OrderService;
import org.junit.Before;
import org.junit.Test;

public class OrderServiceImplTest {

  private OrderServiceImpl t;

  private OrderStore orderStoreMock;
  private VersionedEntityTable<Ask> mockAskOrderTable;
  private LongKeyFactory<Ask> mockAskOrderDbKeyFactory;
  private VersionedEntityTable<Bid> mockBidOrderTable;
  private LongKeyFactory<Bid> mockBidOrderDbKeyFactory;

  @Before
  public void setUp() {
    orderStoreMock = mock(OrderStore.class);
    mockAskOrderTable = mock(VersionedEntityTable.class);
    mockAskOrderDbKeyFactory = mock(LongKeyFactory.class);
    mockBidOrderTable = mock(VersionedEntityTable.class);
    mockBidOrderDbKeyFactory = mock(LongKeyFactory.class);

    when(orderStoreMock.getAskOrderTable()).thenReturn(mockAskOrderTable);
    when(orderStoreMock.getAskOrderDbKeyFactory()).thenReturn(mockAskOrderDbKeyFactory);
    when(orderStoreMock.getBidOrderTable()).thenReturn(mockBidOrderTable);
    when(orderStoreMock.getBidOrderDbKeyFactory()).thenReturn(mockBidOrderDbKeyFactory);

    t = new OrderServiceImpl(orderStoreMock);
  }

  @Test
  public void getAskOrder() {
    final BurstKey mockAskKey = mock(BurstKey.class);
    final Ask mockAsk = mock(Ask.class);

    final long askKey = 123l;

    when(mockAskOrderDbKeyFactory.newKey(eq(askKey))).thenReturn(mockAskKey);
    when(mockAskOrderTable.get(eq(mockAskKey))).thenReturn(mockAsk);

    assertEquals(mockAsk, t.getAskOrder(askKey));
  }

  @Test
  public void getBidOrder() {
    final BurstKey mockBidKey = mock(BurstKey.class);
    final Bid mockBid = mock(Bid.class);

    final long bidKey = 123l;

    when(mockBidOrderDbKeyFactory.newKey(eq(bidKey))).thenReturn(mockBidKey);
    when(mockBidOrderTable.get(eq(mockBidKey))).thenReturn(mockBid);

    assertEquals(mockBid, t.getBidOrder(bidKey));
  }

  @Test
  public void getAllAskOrders() {
    final int from = 1;
    final int to = 5;

    final BurstIterator<Ask> mockAskIterator = mock(BurstIterator.class);

    when(mockAskOrderTable.getAll(eq(from), eq(to))).thenReturn(mockAskIterator);

    assertEquals(mockAskIterator, t.getAllAskOrders(from, to));
  }

  @Test
  public void getAllBidOrders() {
    final int from = 1;
    final int to = 5;

    final BurstIterator<Bid> mockBidIterator = mock(BurstIterator.class);

    when(mockBidOrderTable.getAll(eq(from), eq(to))).thenReturn(mockBidIterator);

    assertEquals(mockBidIterator, t.getAllBidOrders(from, to));
  }

  @Test
  public void getSortedBidOrders() {
    final long assetId = 123l;
    final int from = 1;
    final int to = 5;

    final BurstIterator<Bid> mockBidIterator = mock(BurstIterator.class);

    when(orderStoreMock.getSortedBids(eq(assetId), eq(from), eq(to))).thenReturn(mockBidIterator);

    assertEquals(mockBidIterator, t.getSortedBidOrders(assetId, from, to));
  }
}
