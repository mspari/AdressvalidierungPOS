-- statement to get all adresses
select a.AddressID, a.Street, a.HouseNr, z.ZipCode, z.CityName, r.RegionName, c.CountryLong
from Address a
	inner join Region r on(a.Region = r.RegionID)
    inner join ZipCode z on (a.ZipCode = z.ZipCode)
    inner join Country c on (a.Country = c.CountryShort);

