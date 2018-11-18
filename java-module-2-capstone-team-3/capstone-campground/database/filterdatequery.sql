SELECT * FROM site where site.site_id NOT IN (
        SELECT site.site_id from site
        INNER JOIN campground 
        ON site.campground_id = campground.campground_id
        INNER JOIN reservation ON site.site_id = reservation.site_id
        WHERE site.campground_id = 7 AND reservation_id IS NULL 
        OR NOT
        ('10/30/2018' NOT BETWEEN from_date AND to_date
        AND '11/5/2018' NOT BETWEEN from_date AND to_date 
        AND from_date NOT BETWEEN '10/30/2018' AND '11/5/2018'))
        AND site.campground_id = 7 
        order by site.site_id;
        
        
        
        
        
        
SELECT site.site_id from site 
        INNER JOIN campground ON site.campground_id = campground.campground_id
        LEFT OUTER JOIN reservation ON site.site_id = reservation.site_id
        WHERE site.campground_id = 1 AND reservation_id IS NULL
        OR ('11/5/2018' - from_date > 0 AND to_date - '10/30/2018' > 0);
				
				