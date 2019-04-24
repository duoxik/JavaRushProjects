package com.duoxik.tasks.logparser;

import com.javarush.task.task39.task3913.query.*;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogParser implements IPQuery, UserQuery, DateQuery, EventQuery, QLQuery {

    private List<File> logs;

    public LogParser(Path logDir) {
        this.logs = new ArrayList<>();
        for (File file : logDir.toFile().listFiles()) {
            if (file.getName().matches(".*[.]log")) {
                logs.add(file);
            }
        }
    }

    private Set<String> getIpsSet(Set<LogRecord> records) {
        Set<String> ips = new HashSet<>();
        for (LogRecord record : records)
            ips.add(record.getIp());

        return ips;
    }

    private Set<String> getUsersSet(Set<LogRecord> records) {
        Set<String> users = new HashSet<>();
        for (LogRecord record : records)
            users.add(record.getUser());

        return users;
    }

    private Set<Event> getEventsSet(Set<LogRecord> records) {
        Set<Event> events = new HashSet<>();
        for (LogRecord record : records)
            events.add(record.getEvent());

        return events;
    }

    private Set<Date> getDatesSet(Set<LogRecord> records) {
        Set<Date> dates = new HashSet<>();
        for (LogRecord record : records)
            dates.add(record.getDate());

        return dates;
    }

    private Set<Integer> getTasksSet(Set<LogRecord> records) {
        Set<Integer> tasks = new HashSet<>();
        for (LogRecord record : records)
            tasks.add(record.getTaskId());

        return tasks;
    }

    private Set<Status> getStatusesSet(Set<LogRecord> records) {
        Set<Status> statuses = new HashSet<>();
        for (LogRecord record : records)
            statuses.add(record.getStatus());

        return statuses;
    }

    private Set<Object> getObjectsSet(RecordField returnValue, String ip, String user, Event event, Integer taskId, Status status, Date after, Date before) {

        Set<Object> resultObjects = new HashSet<>();
        switch (returnValue) {
            case IP:
                for (LogRecord record : getRecordsByAttributes(ip, user, event, taskId, status, after, before))
                    resultObjects.add(record.getIp());
                break;
            case USER:
                for (LogRecord record : getRecordsByAttributes(ip, user, event, taskId, status, after, before))
                    resultObjects.add(record.getUser());
                break;
            case DATE:
                for (LogRecord record : getRecordsByAttributes(ip, user, event, taskId, status, after, before))
                    resultObjects.add(record.getDate());
                break;
            case EVENT:
                for (LogRecord record : getRecordsByAttributes(ip, user, event, taskId, status, after, before))
                    resultObjects.add(record.getEvent());
                break;
            case STATUS:
                for (LogRecord record : getRecordsByAttributes(ip, user, event, taskId, status, after, before))
                    resultObjects.add(record.getStatus());
                break;
        }

        return resultObjects;
    }

    private Set<LogRecord> getRecordsByAttributes(String ip, String user, Event event, Integer taskId, Status status, Date after, Date before) {

        Set<LogRecord> records = new HashSet<>();

        for (File logFile : logs) {

            try (BufferedReader bf = new BufferedReader(new FileReader(logFile))) {

                while (bf.ready()) {

                    String logLine = bf.readLine();

                    String[] parts = logLine.split("\\t");

                    LogRecord record = parseLogLine(parts);

                    if ((after == null || record.getDate().after(after))
                            && (before == null || record.getDate().before(before))
                            && (ip == null || ip.equals(record.getIp()))
                            && (user == null || user.equals(record.getUser()))
                            && (event == null || event == record.getEvent())
                            && (taskId == null || taskId == record.getTaskId())
                            && (status == null || status == record.getStatus())
                    ) {
                        records.add(record);
                    }
                }
            } catch (ParseException ex) {
                ex.printStackTrace();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return records;
    }

    private LogRecord parseLogLine(String[] parts) throws ParseException {

        String ip = parts[0];
        String user = parts[1];
        Date date = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(parts[2]);

        Event event = null;
        int taskId = -1;
        switch (parts[3]) {
            case "LOGIN":
                event = Event.LOGIN;
                break;
            case "DOWNLOAD_PLUGIN":
                event = Event.DOWNLOAD_PLUGIN;
                break;
            case "WRITE_MESSAGE":
                event = Event.WRITE_MESSAGE;
                break;
            default:

                if (parts[3].matches("SOLVE_TASK.*")) {
                    event = Event.SOLVE_TASK;
                } else if (parts[3].matches("DONE_TASK.*")) {
                    event = Event.DONE_TASK;
                }

                taskId = Integer.parseInt(parts[3].split(" ")[1]);
        }

        Status status = null;
        switch (parts[4]) {
            case "OK":
                status = Status.OK;
                break;
            case "FAILED":
                status = Status.FAILED;
                break;
            case "ERROR":
                status = Status.ERROR;
                break;
        }

        return new LogRecord(ip, user, date, event, taskId, status);
    }

    @Override
    public int getNumberOfUniqueIPs(Date after, Date before) {
        return getUniqueIPs(after, before).size();
    }

    @Override
    public Set<String> getUniqueIPs(Date after, Date before) {
        return getIpsSet(getRecordsByAttributes(null, null, null, null, null, after, before));
    }

    @Override
    public Set<String> getIPsForUser(String user, Date after, Date before) {
        return getIpsSet(getRecordsByAttributes(user, null, null, null, null, after, before));
    }

    @Override
    public Set<String> getIPsForEvent(Event event, Date after, Date before) {
        return getIpsSet(getRecordsByAttributes(null, null, event, null, null, after, before));
    }

    @Override
    public Set<String> getIPsForStatus(Status status, Date after, Date before) {
        return getIpsSet(getRecordsByAttributes(null, null, null, null, status, after, before));
    }

    @Override
    public Set<String> getAllUsers() {
        return getUsersSet(getRecordsByAttributes(null, null, null, null, null, null, null));
    }

    @Override
    public int getNumberOfUsers(Date after, Date before) {
        return getUsersSet(getRecordsByAttributes(null, null, null, null, null, after, before)).size();
    }

    @Override
    public int getNumberOfUserEvents(String user, Date after, Date before) {
        return getEventsSet(getRecordsByAttributes(null, user, null, null, null, after, before)).size();
    }

    @Override
    public Set<String> getUsersForIP(String ip, Date after, Date before) {
        return getUsersSet(getRecordsByAttributes(ip, null, null, null, null, after, before));
    }

    @Override
    public Set<String> getLoggedUsers(Date after, Date before) {
        return getUsersSet(getRecordsByAttributes(null, null, Event.LOGIN, null, null, after, before));
    }

    @Override
    public Set<String> getDownloadedPluginUsers(Date after, Date before) {
        return getUsersSet(getRecordsByAttributes(null, null, Event.DOWNLOAD_PLUGIN, null, null, after, before));
    }

    @Override
    public Set<String> getWroteMessageUsers(Date after, Date before) {
        return getUsersSet(getRecordsByAttributes(null, null, Event.WRITE_MESSAGE, null, null, after, before));
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before) {
        return getUsersSet(getRecordsByAttributes(null, null, Event.SOLVE_TASK, null, null, after, before));
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before, int task) {
        return getUsersSet(getRecordsByAttributes(null, null, Event.SOLVE_TASK, task, null, after, before));
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before) {
        return getUsersSet(getRecordsByAttributes(null, null, Event.DONE_TASK, null, null, after, before));
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before, int task) {
        return getUsersSet(getRecordsByAttributes(null, null, Event.DONE_TASK, task, null, after, before));
    }

    @Override
    public Set<Date> getDatesForUserAndEvent(String user, Event event, Date after, Date before) {
        return getDatesSet(getRecordsByAttributes(null, user, event, null, null, after, before));
    }

    @Override
    public Set<Date> getDatesWhenSomethingFailed(Date after, Date before) {
        return getDatesSet(getRecordsByAttributes(null, null, null, null, Status.FAILED, after, before));
    }

    @Override
    public Set<Date> getDatesWhenErrorHappened(Date after, Date before) {
        return getDatesSet(getRecordsByAttributes(null, null, null, null, Status.ERROR, after, before));
    }

    @Override
    public Date getDateWhenUserLoggedFirstTime(String user, Date after, Date before) {

        Set<Date> dates = getDatesSet(getRecordsByAttributes(null, user, Event.LOGIN, null, null, after, before));
        return (dates.size() > 0) ? Collections.min(dates) : null;
    }

    @Override
    public Date getDateWhenUserSolvedTask(String user, int task, Date after, Date before) {

        Set<Date> dates = getDatesSet(getRecordsByAttributes(null, user, Event.SOLVE_TASK, task, null, after, before));
        return (dates.size() > 0) ? Collections.min(dates) : null;
    }

    @Override
    public Date getDateWhenUserDoneTask(String user, int task, Date after, Date before) {
        Set<Date> dates = getDatesSet(getRecordsByAttributes(null, user, Event.DONE_TASK, task, null, after, before));
        return (dates.size() > 0) ? Collections.min(dates) : null;
    }

    @Override
    public Set<Date> getDatesWhenUserWroteMessage(String user, Date after, Date before) {
        return getDatesSet(getRecordsByAttributes(null, user, Event.WRITE_MESSAGE, null, null, after, before));
    }

    @Override
    public Set<Date> getDatesWhenUserDownloadedPlugin(String user, Date after, Date before) {
        return getDatesSet(getRecordsByAttributes(null, user, Event.DOWNLOAD_PLUGIN, null, null, after, before));
    }

    @Override
    public int getNumberOfAllEvents(Date after, Date before) {
        return getAllEvents(after, before).size();
    }

    @Override
    public Set<Event> getAllEvents(Date after, Date before) {
        return getEventsSet(getRecordsByAttributes(null, null, null, null, null, after, before));
    }

    @Override
    public Set<Event> getEventsForIP(String ip, Date after, Date before) {
        return getEventsSet(getRecordsByAttributes(ip, null, null, null, null, after, before));
    }

    @Override
    public Set<Event> getEventsForUser(String user, Date after, Date before) {
        return getEventsSet(getRecordsByAttributes(null, user, null, null, null, after, before));
    }

    @Override
    public Set<Event> getFailedEvents(Date after, Date before) {
        return getEventsSet(getRecordsByAttributes(null, null, null, null, Status.FAILED, after, before));
    }

    @Override
    public Set<Event> getErrorEvents(Date after, Date before) {
        return getEventsSet(getRecordsByAttributes(null, null, null, null, Status.ERROR, after, before));
    }

    @Override
    public int getNumberOfAttemptToSolveTask(int task, Date after, Date before) {
        return getRecordsByAttributes(null, null, Event.SOLVE_TASK, task, null, after, before).size();
    }

    @Override
    public int getNumberOfSuccessfulAttemptToSolveTask(int task, Date after, Date before) {
        return getRecordsByAttributes(null, null, Event.DONE_TASK, task, null, after, before).size();
    }

    @Override
    public Map<Integer, Integer> getAllSolvedTasksAndTheirNumber(Date after, Date before) {

        Set<Integer> tasks = getTasksSet(getRecordsByAttributes(null, null, Event.SOLVE_TASK, null, null, after, before));
        Map<Integer, Integer> resultMap = new HashMap<>();

        for (Integer task : tasks) {
            int attempts = getNumberOfAttemptToSolveTask(task, after, before);
            resultMap.put(task, attempts);
        }

        return resultMap;
    }

    @Override
    public Map<Integer, Integer> getAllDoneTasksAndTheirNumber(Date after, Date before) {
        Set<Integer> tasks = getTasksSet(getRecordsByAttributes(null, null, Event.DONE_TASK, null, null, after, before));
        Map<Integer, Integer> resultMap = new HashMap<>();

        for (Integer task : tasks) {
            int attempts = getNumberOfSuccessfulAttemptToSolveTask(task, after, before);
            resultMap.put(task, attempts);
        }

        return resultMap;
    }

    @Override
    public Set<Object> execute(String query) {
        Set<Object> objects = null;

        String ip = null;
        String user = null;
        Event event = null;
        Status status = null;
        Date after = null;
        Date before = null;

        Matcher getMatcher = Pattern.compile("get \\w+").matcher(query);
        if (getMatcher.find()) {
            RecordField resultType = RecordField.getInstance(query.substring(4, getMatcher.end()));

            Matcher forMatcher = Pattern.compile("for \\w+ = \".*\"").matcher(query);

            if (forMatcher.find()) {

                String[] forQueryParts = query.substring(forMatcher.start(), forMatcher.end()).split(" ");

                int firstBracket = query.indexOf("\"") + 1;
                int secondBracket = query.indexOf("\"", firstBracket + 1);
                String value = query.substring(firstBracket, secondBracket);
                switch (RecordField.getInstance(forQueryParts[1])) {
                    case IP:
                        ip = value;
                        break;
                    case USER:
                        user = value;
                        break;
                    case EVENT:
                        event = Event.getInstance(value);
                        break;
                    case STATUS:
                        status = Status.getInstance(value);
                        break;
                    case DATE:
                        try {
                            Date date = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(value);
                            after = new Date(date.getTime() - 1);
                            before = new Date(date.getTime() + 1);
                            break;
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                }

                Matcher betweenMatcher = Pattern.compile("and date between \".*\" and \".*\"").matcher(query);
                if (betweenMatcher.find()) {

                    String betweenQuery = query.substring(betweenMatcher.start());
                    Matcher dateMatcher = Pattern.compile("\"[\\d.: ]*\"").matcher(betweenQuery);

                    dateMatcher.find();
                    Date date1 = null;
                    try {
                        date1 = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(betweenQuery.substring(dateMatcher.start() + 1, dateMatcher.end() -1));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    dateMatcher.find();
                    Date date2 = null;
                    try {
                        date2 = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(betweenQuery.substring(dateMatcher.start() + 1, dateMatcher.end() -1));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (after == null || after.before(date1)) {
                        after = date1;
                    }

                    if (before == null || before.after(date2)) {
                        before = date2;
                    }
                }
            }

            objects = getObjectsSet(resultType, ip, user, event, null, status, after, before);
        }
        return objects;
    }

    private enum RecordField {
        IP, USER, DATE, EVENT, STATUS;

        private static RecordField getInstance(String fieldName) {
            for (RecordField field : values()) {
                if (field.toString().toLowerCase().equals(fieldName)) {
                    return field;
                }
            }

            return null;
        }
    }

    private static class LogRecord {

        private String ip;
        private String user;
        private Date date;
        private Event event;
        private int taskId;
        private Status status;

        public LogRecord(String ip, String user, Date date, Event event, int taskId, Status status) {
            this.ip = ip;
            this.user = user;
            this.date = date;
            this.event = event;
            this.taskId = taskId;
            this.status = status;
        }

        public String getIp() {
            return ip;
        }

        public String getUser() {
            return user;
        }

        public Date getDate() {
            return date;
        }

        public Event getEvent() {
            return event;
        }

        public int getTaskId() {
            return taskId;
        }

        public Status getStatus() {
            return status;
        }
    }
}