drop table if exists availableCigarettes cascade;
drop table if exists availableBuddyCigarettes cascade;
drop table if exists limits cascade;
drop table if exists buddyLimits cascade;
drop table if exists settings cascade;
drop table if exists yearData cascade;
drop table if exists monthData cascade;
drop table if exists weekData cascade;


create table availableCigarettes
  (cigarettes 	integer not null unique);

create table availableBuddyCigarettes
  (cigarettes 	integer not null unique); 

create table limits
  (dailyLimit	integer not null);

create table buddyLimits
    (buddyDailyLimit integer not null);


create table settings
    (notifyAll boolean,
    appVibration boolean,
    packVibration boolean,
    userLimits boolean,
    buddyLimits boolean,
    progress boolean);

create table yearData
    (january float,
    february float,
    march float,
    april float
    may float,
    june float,
    july float,
    august float,
    september float,
    october float,
    november float,
    december float);

create table monthData
    (day1 integer,
    day2 integer,
    day3 integer,
    day4 integer,
    day5 integer,
    day6 integer,
    day7 integer,
    day8 integer,
    day9 integer,
    day10 integer,
    day11 integer,
    day12 integer,
    day13 integer,
    day14 integer,
    day15 integer,
    day16 integer,
    day17 integer,
    day18 integer,
    day19 integer,
    day20 integer,
    day21 integer,
    day22 integer,
    day23 integer,
    day24 integer,
    day25 integer,
    day26 integer,
    day27 integer,
    day28 integer,
    day29 integer,
    day30 integer,
    day31 integer);

create table weekData
    (monday integer,
    tuesday integer,
    wendnesday integer,
    thursday integer,
    friday integer,
    saturday integer,
    sunday integer);

