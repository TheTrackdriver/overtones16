local Overtones16 = {}
local ControlSpec = require 'controlspec'
local Formatters = require 'formatters'

function Overtones16.add_params()
  params:add_separator("envelope")
  params:add_control("amp", "main level", controlspec.new(0 , 1, 'lin', 0 , 0.57, ''))
  params:set_action("amp", function(x) engine.amp(x) end)
  params:add_control("attack", "attack", controlspec.new(0.01, 10, 'lin', 0, 0.01, 's'))
  params:set_action("attack", function(x) engine.attack(x) end)
  params:add_control("decay", "decay", controlspec.new(0.1, 10, 'lin', 0, 0.3, 's'))
  params:set_action("decay", function(x) engine.decay(x) end)
  params:add_control("sustain", "sustain", controlspec.new(0, 1, 'lin', 0, 0.7, ''))
  params:set_action("sustain", function(x) engine.sustain(x) end)
  params:add_control("release", "release", controlspec.new(0.1, 10, 'lin', 0, 3, 's'))
  params:set_action("release", function(x) engine.release(x) end)
  
  params:add_separator("morph")
  params:add_control("morphMixVal", "lfo>rnd>env", controlspec.new(0 , 2, 'lin', 0 , 0, ''))
  params:set_action("morphMixVal", function(x) engine.morphMixVal(x) end)
  params:add_control("morphRate", "morph rate", controlspec.new(0.1 , 20, 'lin', 0 , 4, 's'))
  params:set_action("morphRate", function(x) engine.morphRate(x) end)
  params:add_control("morphStart", "morph start", controlspec.new(0, 3, 'lin', 0, 0, ''))
  params:set_action("morphStart", function(x) engine.morphStart(x) end)
  params:add_control("morphEnd", "morph end", controlspec.new(0, 3, 'lin', 0, 0, ''))
  params:set_action("morphEnd", function(x) engine.morphEnd(x) end)
  
  params:add_separator("modulation")
  params:add_control("panwidth", "pan mod width", controlspec.new(0, 1, 'lin', 0, 0, ''))
  params:set_action("panwidth", function(x) engine.panwidth(x) end)
  params:add_control("panrate", "pan mod rate", controlspec.new(0.1, 20, 'lin', 0, 8, ''))
  params:set_action("panrate", function(x) engine.panrate(x) end)
  params:add_control("pitchmod", "pitch mod depth", controlspec.new(0, 26, 'lin', 0, 0, 'hz'))
  params:set_action("pitchmod", function(x) engine.pitchmod(x) end)
  params:add_control("pitchrate", "pitch mod rate", controlspec.new(0.1, 20, 'lin', 0, 4, ''))
  params:set_action("pitchrate", function(x) engine.pitchrate(x) end)
  
  params:add_separator("partials")
  params:add_group("partialsgroup", "partials", 68)

  params:add_separator("snapshot [1]")
  params:add_control("s1p1", "[1] partial 1", controlspec.new(0, 1, 'lin', 0, 1, ''))
  for i = 2,16 do
    params:add_control("s1p"..i, "[1] partial "..i, controlspec.new(0, 1, 'lin', 0, 0, ''))
  end
  
  params:add_separator("snapshot [2]")
  for i = 1,16 do
    params:add_control("s2p"..i, "[2] partial "..i, controlspec.new(0, 1, 'lin', 0, 0, ''))
  end

  params:add_separator("snapshot [3]")
  for i = 1,16 do
    params:add_control("s3p"..i, "[3] partial "..i, controlspec.new(0, 1, 'lin', 0, 0, ''))
  end

  params:add_separator("snapshot [4]")
  for i = 1,16 do
    params:add_control("s4p"..i, "[4] partial "..i, controlspec.new(0, 1, 'lin', 0, 0, ''))
  end
  
  params:set_action("s1p1", function(x) engine.s1p1(x) end)
  params:set_action("s1p2", function(x) engine.s1p2(x) end)
  params:set_action("s1p3", function(x) engine.s1p3(x) end)
  params:set_action("s1p4", function(x) engine.s1p4(x) end)
  params:set_action("s1p5", function(x) engine.s1p5(x) end)
  params:set_action("s1p6", function(x) engine.s1p6(x) end)
  params:set_action("s1p7", function(x) engine.s1p7(x) end)
  params:set_action("s1p8", function(x) engine.s1p8(x) end)
  params:set_action("s1p9", function(x) engine.s1p9(x) end)
  params:set_action("s1p10", function(x) engine.s1p10(x) end)
  params:set_action("s1p11", function(x) engine.s1p11(x) end)
  params:set_action("s1p12", function(x) engine.s1p12(x) end)
  params:set_action("s1p13", function(x) engine.s1p13(x) end)
  params:set_action("s1p14", function(x) engine.s1p14(x) end)
  params:set_action("s1p15", function(x) engine.s1p15(x) end)
  params:set_action("s1p16", function(x) engine.s1p16(x) end)
  
  params:set_action("s2p1", function(x) engine.s2p1(x) end)
  params:set_action("s2p2", function(x) engine.s2p2(x) end)
  params:set_action("s2p3", function(x) engine.s2p3(x) end)
  params:set_action("s2p4", function(x) engine.s2p4(x) end)
  params:set_action("s2p5", function(x) engine.s2p5(x) end)
  params:set_action("s2p6", function(x) engine.s2p6(x) end)
  params:set_action("s2p7", function(x) engine.s2p7(x) end)
  params:set_action("s2p8", function(x) engine.s2p8(x) end)
  params:set_action("s2p9", function(x) engine.s2p9(x) end)
  params:set_action("s2p10", function(x) engine.s2p10(x) end)
  params:set_action("s2p11", function(x) engine.s2p11(x) end)
  params:set_action("s2p12", function(x) engine.s2p12(x) end)
  params:set_action("s2p13", function(x) engine.s2p13(x) end)
  params:set_action("s2p14", function(x) engine.s2p14(x) end)
  params:set_action("s2p15", function(x) engine.s2p15(x) end)
  params:set_action("s2p16", function(x) engine.s2p16(x) end)
  
  params:set_action("s3p1", function(x) engine.s3p1(x) end)
  params:set_action("s3p2", function(x) engine.s3p2(x) end)
  params:set_action("s3p3", function(x) engine.s3p3(x) end)
  params:set_action("s3p4", function(x) engine.s3p4(x) end)
  params:set_action("s3p5", function(x) engine.s3p5(x) end)
  params:set_action("s3p6", function(x) engine.s3p6(x) end)
  params:set_action("s3p7", function(x) engine.s3p7(x) end)
  params:set_action("s3p8", function(x) engine.s3p8(x) end)
  params:set_action("s3p9", function(x) engine.s3p9(x) end)
  params:set_action("s3p10", function(x) engine.s3p10(x) end)
  params:set_action("s3p11", function(x) engine.s3p11(x) end)
  params:set_action("s3p12", function(x) engine.s3p12(x) end)
  params:set_action("s3p13", function(x) engine.s3p13(x) end)
  params:set_action("s3p14", function(x) engine.s3p14(x) end)
  params:set_action("s3p15", function(x) engine.s3p15(x) end)
  params:set_action("s3p16", function(x) engine.s3p16(x) end)
  
  params:set_action("s4p1", function(x) engine.s4p1(x) end)
  params:set_action("s4p2", function(x) engine.s4p2(x) end)
  params:set_action("s4p3", function(x) engine.s4p3(x) end)
  params:set_action("s4p4", function(x) engine.s4p4(x) end)
  params:set_action("s4p5", function(x) engine.s4p5(x) end)
  params:set_action("s4p6", function(x) engine.s4p6(x) end)
  params:set_action("s4p7", function(x) engine.s4p7(x) end)
  params:set_action("s4p8", function(x) engine.s4p8(x) end)
  params:set_action("s4p9", function(x) engine.s4p9(x) end)
  params:set_action("s4p10", function(x) engine.s4p10(x) end)
  params:set_action("s4p11", function(x) engine.s4p11(x) end)
  params:set_action("s4p12", function(x) engine.s4p12(x) end)
  params:set_action("s4p13", function(x) engine.s4p13(x) end)
  params:set_action("s4p14", function(x) engine.s4p14(x) end)
  params:set_action("s4p15", function(x) engine.s4p15(x) end)
  params:set_action("s4p16", function(x) engine.s4p16(x) end)

  params:bang()
end

 -- we return these engine-specific Lua functions back to the host script:
return Overtones16