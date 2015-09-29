
vaseHeight = 300;
vaseHeight = 120;

// this sets how many cutouts are in the vase
charmCount = 26;

spursCharmStl = "../../lamp-shade-scallops/spur/spurs-a.stl";
spursCharmXYScale = 0.2;  // spur scale
spursCharmDepthScale = 20.2;   // oshw depth scale

oshwCharmStl = "basketball/basketball-d/basketball-d.stl";
oshwCharmXYScale = 0.8;
oshwCharmDepthScale = 40.2;

starCharmStl = "star.stl";
starCharmXYScale = 0.7;
starCharmDepthScale = 40.2;

charmStls = [
                spursCharmStl,
                oshwCharmStl,
                starCharmStl
            ];

charmXYScales = [
                    spursCharmXYScale,
                    oshwCharmXYScale,
                    starCharmXYScale
                ];
                
charmDepthScales = [
                        spursCharmDepthScale,
                        oshwCharmDepthScale,
                        starCharmDepthScale
                   ];


uniqueCharmCount = len(charmStls);

charmIndex = -1;

difference()
{
    // vase
    difference()
    {
        // outer vase
        translate([0,0,0])
        cylinder (h = vaseHeight, r=55, $fn=100);
        
        // remvoed inner vase centered
        translate([0,0,5])
        cylinder (h = vaseHeight, r=54, $fn=100);
    }
    
    y = 30;

    // lucky charms!
    for( i = [0 : charmCount] )
    {
        single_rand = rands(0,2,1)[0];
        charmIndex = round(single_rand);
        
//echo(charmIndex);

//charmIndex = 1;
        
        rotate([
                90, 
                0,
                i * 38
        ])

        // normally x,y,z - but here y moves the charms up and down
        translate([15, 5 * i, 30])
        scale([charmXYScales[charmIndex],
               charmXYScales[charmIndex], 
               charmDepthScales[charmIndex]
              ])
        import(charmStls[charmIndex]);
    }
}

        