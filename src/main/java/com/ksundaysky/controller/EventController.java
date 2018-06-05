package com.ksundaysky.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ksundaysky.model.Event;
import com.ksundaysky.model.EventWorkdays;
import com.ksundaysky.model.Visit;
import com.ksundaysky.model.Workdays;
import com.ksundaysky.repository.EventRepository;
import com.ksundaysky.service.VisitService;
import com.ksundaysky.service.WorkdaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
class EventController {

    @Autowired
    EventRepository eventRepository;
    @Autowired
    VisitService visitService;

    @Autowired
    LoginController loginController;
    @Autowired
    WorkdaysService workdaysService;

    @RequestMapping(value="/allevents", method=RequestMethod.GET)
    public List<Event> allEvents() {

        List<Visit> visits = visitService.findAll();

        List<Event> events = new ArrayList<Event>();

        Long id =new Long(1);
        for(Visit visit : visits)
        {
            String pattern = "yyyy-MM-dd HH:mm";
            DateFormat df = new SimpleDateFormat(pattern);
            try {
              //  System.out.println();

                Date start = df.parse(visit.receipt_date_start);
                System.out.println("**********************************************"+start+"************************************");
                Date end  = df.parse(visit.receipt_date_end);
                String title = visit.getEstimated_description();
                String description = "bardzo wazne informacje ttaj";//visit.getProductName() + " " +visit.getReceipt_type();
                //Long id =Long.parseLong( visit.getId());

                Event event = new Event(id,title,description,visit.receipt_date_start,visit.receipt_date_end,null);
                events.add(event);
                id++;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        return events;
    }

    @RequestMapping(value="/userEvents", method=RequestMethod.GET)
    public List<Event> userEvents() {

        List<Visit> visits = visitService.findVisitsByEmployerId(loginController.loggedInUser.getId());

        List<Event> events = new ArrayList<Event>();

        Long id =new Long(1);

        for(Visit visit : visits)
        {
            String pattern = "yyyy-MM-dd HH:mm";
            DateFormat df = new SimpleDateFormat(pattern);
            try {
                //  System.out.println();

                Date start = df.parse(visit.receipt_date_start);
                System.out.println("**********************************************"+start+"************************************");
                Date end  = df.parse(visit.receipt_date_end);
                String title = visit.getEstimated_description();
                String description = "bardzo wazne informacje ttaj";//visit.getProductName() + " " +visit.getReceipt_type();
                //Long id =Long.parseLong( visit.getId());

                Event event = new Event(id,title,description,visit.receipt_date_start,visit.receipt_date_end,"#C0392B");
                events.add(event);
                id++;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        return events;
    }


    @RequestMapping(value="/workDays", method=RequestMethod.GET)
    public ArrayList<EventWorkdays> workDays() {


        Workdays workdays = workdaysService.getWorkdaysById(loginController.loggedInUser.getWorkdays_id());


        System.out.println(loginController.loggedInUser.getEmail());
        ArrayList<Integer> arrayList = new ArrayList();
        List<Event> events = new ArrayList<Event>();

        if(workdays.isMonday())
            arrayList.add(1);
        if(workdays.isTuesday())
            arrayList.add(2);
        if(workdays.isWednesday())
            arrayList.add(3);
        if(workdays.isThursday())
            arrayList.add(4);
        if(workdays.isFriday())
            arrayList.add(5);

        int [] dow = new int[arrayList.size()];

        for(int i=0; i<arrayList.size();i++)
        {
            dow[i]=arrayList.get(i);
        }

        System.out.println(arrayList.toString());



        EventWorkdays event = new EventWorkdays("08:00","16:00",arrayList.toString(),"Working");

        ArrayList<EventWorkdays> arr = new ArrayList<>();
        arr.add(event);
        return arr;
    }

    @RequestMapping(value="/event", method=RequestMethod.POST)
    public Event addEvent(@RequestBody Event event) {
        Event created = eventRepository.save(event);
        return created;
    }

    @RequestMapping(value="/event", method=RequestMethod.PATCH)
    public Event updateEvent(@RequestBody Event event) {
        return eventRepository.save(event);
    }

    @RequestMapping(value="/event", method=RequestMethod.DELETE)
    public void removeEvent(@RequestBody Event event) {
        eventRepository.delete(event);
    }
    //
    @RequestMapping(value="/events", method=RequestMethod.GET)
    public List<Event> getEventsInRange(@RequestParam(value = "start", required = true) String start,
                                        @RequestParam(value = "end", required = true) String end) {
        Date startDate = null;
        Date endDate = null;
        SimpleDateFormat inputDateFormat=new SimpleDateFormat("yyyy-MM-dd");

        try {
            startDate = inputDateFormat.parse(start);
        } catch (ParseException e) {
            throw new BadDateFormatException("bad start date: " + start);
        }

        try {
            endDate = inputDateFormat.parse(end);
        } catch (ParseException e) {
            throw new BadDateFormatException("bad end date: " + end);
        }

        //return eventRepository.findByDatesBetween(startDate, endDate);
        return eventRepository.findAll();
    }

}