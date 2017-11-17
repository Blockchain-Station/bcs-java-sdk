package com.enjin.coin.sdk.service.events;

import com.enjin.coin.sdk.vo.event.GetEventRequestVO;
import com.enjin.coin.sdk.vo.event.GetEventResponseVO;
import com.enjin.coin.sdk.vo.event.ListEventsRequestVO;
import com.enjin.coin.sdk.vo.event.ListEventsResponseVO;

/**
 * Events service interface.
 */
public interface EventsService {

    /**
     * Method to get an event.
     *
     * @param request - get event request vo
     * @return - GetEventResponseVO
     */
    GetEventResponseVO getEvent(GetEventRequestVO request);

    /**
     * Method to list the events.
     *
     * @param request - list events request vo
     * @return - ListEventsResponseVO
     */
    ListEventsResponseVO listEvents(ListEventsRequestVO request);

}
