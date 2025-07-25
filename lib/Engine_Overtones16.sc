Engine_Overtones16 : CroneEngine {

	classvar maxNumVoices = 8;
	var voiceGroup,
	voiceList,

	lastFreq = 0,
	freq = 220,
	attack = 0.01,
	decay = 0.3,
	sustain = 0.7,
	release = 5,
	morphMixVal = 0,
	morphRate = 4,
	morphStart = 0,
	morphEnd = 3,
	pitchmod = 0,
	pitchrate = 4,
	panwidth = 0,
	panrate = 8,
	gate = 1,
	amp = 0.75,
	s1p1 = 0, s2p1 = 0, s3p1 = 0, s4p1 = 0,
	s1p2 = 0, s2p2 = 0, s3p2 = 0, s4p2 = 0,
	s1p3 = 0, s2p3 = 0, s3p3 = 0, s4p3 = 0,
	s1p4 = 0, s2p4 = 0, s3p4 = 0, s4p4 = 0,
	s1p5 = 0, s2p5 = 0, s3p5 = 0, s4p5 = 0,
	s1p6 = 0, s2p6 = 0, s3p6 = 0, s4p6 = 0,
	s1p7 = 0, s2p7 = 0, s3p7 = 0, s4p7 = 0,
	s1p8 = 0, s2p8 = 0, s3p8 = 0, s4p8 = 0,
	s1p9 = 0, s2p9 = 0, s3p9 = 0, s4p9 = 0,
	s1p10 = 0, s2p10 = 0, s3p10 = 0, s4p10 = 0,
	s1p11 = 0, s2p11 = 0, s3p11 = 0, s4p11 = 0,
	s1p12 = 0, s2p12 = 0, s3p12 = 0, s4p12 = 0,
	s1p13 = 0, s2p13 = 0, s3p13 = 0, s4p13 = 0,
	s1p14 = 0, s2p14 = 0, s3p14 = 0, s4p14 = 0,
	s1p15 = 0, s2p15 = 0, s3p15 = 0, s4p15 = 0,
	s1p16 = 0, s2p16 = 0, s3p16 = 0, s4p16 = 0;

	*new { arg context, doneCallback;
		^super.new(context, doneCallback);
	}

	alloc {

		voiceGroup = Group.new(context.xg);
		voiceList = List.new();

		// Synth voice
		SynthDef("Overtones16", {
			arg freq = 220,
			s1p1 = 0, s2p1 = 0, s3p1 = 0, s4p1 = 0,
			s1p2 = 0, s2p2 = 0, s3p2 = 0, s4p2 = 0,
			s1p3 = 0, s2p3 = 0, s3p3 = 0, s4p3 = 0,
			s1p4 = 0, s2p4 = 0, s3p4 = 0, s4p4 = 0,
			s1p5 = 0, s2p5 = 0, s3p5 = 0, s4p5 = 0,
			s1p6 = 0, s2p6 = 0, s3p6 = 0, s4p6 = 0,
			s1p7 = 0, s2p7 = 0, s3p7 = 0, s4p7 = 0,
			s1p8 = 0, s2p8 = 0, s3p8 = 0, s4p8 = 0,
			s1p9 = 0, s2p9 = 0, s3p9 = 0, s4p9 = 0,
			s1p10 = 0, s2p10 = 0, s3p10 = 0, s4p10 = 0,
			s1p11 = 0, s2p11 = 0, s3p11 = 0, s4p11 = 0,
			s1p12 = 0, s2p12 = 0, s3p12 = 0, s4p12 = 0,
			s1p13 = 0, s2p13 = 0, s3p13 = 0, s4p13 = 0,
			s1p14 = 0, s2p14 = 0, s3p14 = 0, s4p14 = 0,
			s1p15 = 0, s2p15 = 0, s3p15 = 0, s4p15 = 0,
			s1p16 = 0, s2p16 = 0, s3p16 = 0, s4p16 = 0,
			attack = 0.01,
			decay = 0.3,
			sustain = 0.7,
			release = 5,
			morphMixVal = 0,
			morphRate = 2,
			morphStart = 0,
			morphEnd = 3,
			pitchmod = 0,
			pitchrate = 4,
			panwidth = 0,
			panrate = 8,
			gate = 1,
			amp = 0.75;

			var sinebank = 0,
			partialAmp,
			partial = (1..16),
			snapshot = (1..4),
			oscPresets,
			morphLfo,
			morphRand,
			morphEnv,
			clippedMorph,
			morphMix,
			envelope,
			signal;

			// Array creation with 16 x 4 arrays. Each small array contains four keyframes, or snapshots, of the volume for one partial. "s" = snapshot, "p" = partial:
			oscPresets = [ [ s1p1, s2p1, s3p1, s4p1 ], [ s1p2, s2p2, s3p2, s4p2 ], [ s1p3, s2p3, s3p3, s4p3 ], [ s1p4, s2p4, s3p4, s4p4 ], [ s1p5, s2p5, s3p5, s4p5 ], [ s1p6, s2p6, s3p6, s4p6 ], [ s1p7, s2p7, s3p7, s4p7 ], [ s1p8, s2p8, s3p8, s4p8 ], [ s1p9, s2p9, s3p9, s4p9 ], [ s1p10, s2p10, s3p10, s4p10 ], [ s1p11, s2p11, s3p11, s4p11 ], [ s1p12, s2p12, s3p12, s4p12 ], [ s1p13, s2p13, s3p13, s4p13 ], [ s1p14, s2p14, s3p14, s4p14 ], [ s1p15, s2p15, s3p15, s4p15 ], [ s1p16, s2p16, s3p16, s4p16 ] ];

			// 3 types of morphing. These will morph between up to 4 presets within the oscPresets array:
			morphLfo = LFTri.kr( freq: 1 / morphRate, iphase: 3, mul: 1.0 ).range( morphStart, morphEnd );
			morphRand = LFNoise1.kr( 4 /morphRate ).range( morphStart, morphEnd );
			morphEnv = EnvGen.kr( Env.adsr( attackTime: 0.01, decayTime: morphRate, sustainLevel: 0, releaseTime: release ).range( morphEnd, morphStart ), gate: gate, doneAction: 2 );

			// A mix parameter that allows choosing the type of morphing. The mix goes from lfo>random>env:
			morphMix = LinSelectX.kr( morphMixVal, [morphLfo, morphRand, morphEnv] );
			clippedMorph = morphMix.clip( 0, oscPresets.size - 1 );

			// Pitch modulation:
			freq = freq + LFNoise1.kr( pitchrate ).range( pitchmod * -1, pitchmod );

			// Sinebank creation:
			sinebankOdd = ( { arg i; SinOsc.ar( freq: freq * ( ( ( i + 1 ) * 2 ) - 1 ), mul: LinSelectX.kr( clippedMorph, oscPresets[ i * 2 ] ) ) } ! 8 ).sum;
			sinebankEven = ( { arg i; SinOsc.ar( freq: freq * ( ( i + 1 ) * 2  ), mul: LinSelectX.kr( clippedMorph, oscPresets[ ( i * 2 ) + 1 ] ) ) } ! 8 ).sum;
			//sinebank = { arg i; SinOsc.ar( freq: freq * ( i + 1 ), mul: LinSelectX.kr( clippedMorph, oscPresets[ i ] ) ) } ! 16;

			// Envelope modulating the main volume:
			envelope = EnvGen.kr(Env.adsr( attackTime: attack, decayTime: decay, sustainLevel: sustain, releaseTime: release ), gate: gate, doneAction: 2 );
			signal  = ( Pan2.ar( sinebankOdd, LFNoise1.kr( panrate ).range( panwidth * -1, panwidth ) ) + Pan2.ar( sinebankEven, TRand.kr( panwidth * -1, panwidth, gate ) ) ) * envelope;
			Out.ar( 0, signal * ( amp * 0.2 ) );

		}).add;

		// Commands

		// noteOn(id, freq, vel)
		this.addCommand(\noteOn, "iff", { arg msg;

			var id = msg[1], freq = msg[2], vel = msg[3];
			var voiceToRemove, newVoice;

			// Remove voice if ID matches or there are too many
			voiceToRemove = voiceList.detect{arg item; item.id == id};
			if(voiceToRemove.isNil && (voiceList.size >= maxNumVoices), {
				voiceToRemove = voiceList.detect{arg v; v.gate == 0};
				if(voiceToRemove.isNil, {
					voiceToRemove = voiceList.last;
				});
			});
			if(voiceToRemove.notNil, {
				voiceToRemove.theSynth.set(\gate, 0);
				voiceToRemove.theSynth.set(\killGate, 0);
				voiceList.remove(voiceToRemove);
			});

			if(lastFreq == 0, {
				lastFreq = freq;
			});

			// Add new voice
			context.server.makeBundle(nil, {
				newVoice = (id: id, theSynth: Synth.new(defName: \Overtones16, args: [
					\freq, freq,
					\attack, attack,
					\decay, decay,
					\sustain, sustain,
					\release, release,
					\morphMixVal, morphMixVal,
					\morphRate, morphRate,
					\morphStart, morphStart,
					\morphEnd, morphEnd,
					\pitchmod, pitchmod,
					\pitchrate, pitchrate,
					\panwidth, panwidth,
					\panrate, panrate,
					\vel, vel.linlin(0, 1, 0.2, 1),
					\amp, amp,
					\s1p1, s1p1, \s2p1, s2p1, \s3p1, s3p1, \s4p1, s4p1,
					\s1p2, s1p2, \s2p2, s2p2, \s3p2, s3p2, \s4p2, s4p2,
					\s1p3, s1p3, \s2p3, s2p3, \s3p3, s3p3, \s4p3, s4p3,
					\s1p4, s1p4, \s2p4, s2p4, \s3p4, s3p4, \s4p4, s4p4,
					\s1p5, s1p5, \s2p5, s2p5, \s3p5, s3p5, \s4p5, s4p5,
					\s1p6, s1p6, \s2p6, s2p6, \s3p6, s3p6, \s4p6, s4p6,
					\s1p7, s1p7, \s2p7, s2p7, \s3p7, s3p7, \s4p7, s4p7,
					\s1p8, s1p8, \s2p8, s2p8, \s3p8, s3p8, \s4p8, s4p8,
					\s1p9, s1p9, \s2p9, s2p9, \s3p9, s3p9, \s4p9, s4p9,
					\s1p10, s1p10, \s2p10, s2p10, \s3p10, s3p10, \s4p10, s4p10,
					\s1p11, s1p11, \s2p11,  s2p11, \s3p11, s3p11, \s4p11, s4p11,
					\s1p12, s1p12, \s2p12, s2p12, \s3p12, s3p12, \s4p12, s4p12,
					\s1p13, s1p13, \s2p13, s2p13, \s3p13, s3p13, \s4p13, s4p13,
					\s1p14, s1p14, \s2p14, s2p14, \s3p14, s3p14, \s4p14, s4p14,
					\s1p15, s1p15, \s2p15, s2p15, \s3p15, s3p15, \s4p15, s4p15,
					\s1p16, s1p16, \s2p16, s2p16, \s3p16, s3p16, \s4p16, s4p16;

				], target: voiceGroup).onFree({ voiceList.remove(newVoice); }), gate: 1);
				voiceList.addFirst(newVoice);
				lastFreq = freq;
			});
		});

		// noteOff(id)
		this.addCommand(\noteOff, "i", { arg msg;
			var voice = voiceList.detect{arg v; v.id == msg[1]};
			if(voice.notNil, {
				voice.theSynth.set(\gate, 0);
				voice.gate = 0;
			});
		});

		//synth parameters
		this.addCommand(\amp, "f", { arg msg;
			amp = msg[1];
			voiceGroup.set(\amp, amp);
		});

		this.addCommand(\attack, "f", { arg msg;
			attack = msg[1];
			voiceGroup.set(\attack, attack);
		});

		this.addCommand(\decay, "f", { arg msg;
			decay = msg[1];
			voiceGroup.set(\decay, decay);
		});

		this.addCommand(\sustain, "f", { arg msg;
			sustain = msg[1];
			voiceGroup.set(\sustain, sustain);
		});

		this.addCommand(\release, "f", { arg msg;
			release = msg[1];
			voiceGroup.set(\release, release);
		});

		this.addCommand(\morphMixVal, "f", { arg msg;
			morphMixVal = msg[1];
			voiceGroup.set(\morphMixVal, morphMixVal);
		});

		this.addCommand(\morphRate, "f", { arg msg;
			morphRate = msg[1];
			voiceGroup.set(\morphRate, morphRate);
		});

		this.addCommand(\morphStart, "f", { arg msg;
			morphStart = msg[1];
			voiceGroup.set(\morphStart, morphStart);
		});

		this.addCommand(\morphEnd, "f", { arg msg;
			morphEnd = msg[1];
			voiceGroup.set(\morphEnd, morphEnd);
		});

		this.addCommand(\panwidth, "f", { arg msg;
			panwidth = msg[1];
			voiceGroup.set(\panwidth, panwidth);
		});

		this.addCommand(\panrate, "f", { arg msg;
			panrate = msg[1];
			voiceGroup.set(\panrate, panrate);
		});

		this.addCommand(\pitchmod, "f", { arg msg;
			pitchmod = msg[1];
			voiceGroup.set(\pitchmod, pitchmod);
		});

		this.addCommand(\pitchrate, "f", { arg msg;
			pitchrate = msg[1];
			voiceGroup.set(\pitchrate, pitchrate);
		});

		//partials snapshot 1:
		this.addCommand(\s1p1, "f", { arg msg;
			s1p1 = msg[1];
			voiceGroup.set(\s1p1, s1p1);
		});
		this.addCommand(\s1p2, "f", { arg msg;
			s1p2 = msg[1];
			voiceGroup.set(\s1p2, s1p2);
		});
		this.addCommand(\s1p3, "f", { arg msg;
			s1p3 = msg[1];
			voiceGroup.set(\s1p3, s1p3);
		});
		this.addCommand(\s1p4, "f", { arg msg;
			s1p4 = msg[1];
			voiceGroup.set(\s1p4, s1p4);
		});
		this.addCommand(\s1p5, "f", { arg msg;
			s1p5 = msg[1];
			voiceGroup.set(\s1p5, s1p5);
		});
		this.addCommand(\s1p6, "f", { arg msg;
			s1p6 = msg[1];
			voiceGroup.set(\s1p6, s1p6);
		});
		this.addCommand(\s1p7, "f", { arg msg;
			s1p7 = msg[1];
			voiceGroup.set(\s1p7, s1p7);
		});
		this.addCommand(\s1p8, "f", { arg msg;
			s1p8 = msg[1];
			voiceGroup.set(\s1p8, s1p8);
		});
		this.addCommand(\s1p9, "f", { arg msg;
			s1p9 = msg[1];
			voiceGroup.set(\s1p9, s1p9);
		});
		this.addCommand(\s1p10, "f", { arg msg;
			s1p10 = msg[1];
			voiceGroup.set(\s1p10, s1p10);
		});
		this.addCommand(\s1p11, "f", { arg msg;
			s1p11 = msg[1];
			voiceGroup.set(\s1p11, s1p11);
		});
		this.addCommand(\s1p12, "f", { arg msg;
			s1p12 = msg[1];
			voiceGroup.set(\s1p12, s1p12);
		});
		this.addCommand(\s1p13, "f", { arg msg;
			s1p13 = msg[1];
			voiceGroup.set(\s1p13, s1p13);
		});
		this.addCommand(\s1p14, "f", { arg msg;
			s1p14 = msg[1];
			voiceGroup.set(\s1p14, s1p14);
		});
		this.addCommand(\s1p15, "f", { arg msg;
			s1p15 = msg[1];
			voiceGroup.set(\s1p15, s1p15);
		});
		this.addCommand(\s1p16, "f", { arg msg;
			s1p16 = msg[1];
			voiceGroup.set(\s1p16, s1p16);
		});

		//partials snapshot 2:
		this.addCommand(\s2p1, "f", { arg msg;
			s2p1 = msg[1];
			voiceGroup.set(\s2p1, s2p1);
		});
		this.addCommand(\s2p2, "f", { arg msg;
			s2p2 = msg[1];
			voiceGroup.set(\s2p2, s2p2);
		});
		this.addCommand(\s2p3, "f", { arg msg;
			s2p3 = msg[1];
			voiceGroup.set(\s2p3, s2p3);
		});
		this.addCommand(\s2p4, "f", { arg msg;
			s2p4 = msg[1];
			voiceGroup.set(\s2p4, s2p4);
		});
		this.addCommand(\s2p5, "f", { arg msg;
			s2p5 = msg[1];
			voiceGroup.set(\s2p5, s2p5);
		});
		this.addCommand(\s2p6, "f", { arg msg;
			s2p6 = msg[1];
			voiceGroup.set(\s2p6, s2p6);
		});
		this.addCommand(\s2p7, "f", { arg msg;
			s2p7 = msg[1];
			voiceGroup.set(\s2p7, s2p7);
		});
		this.addCommand(\s2p8, "f", { arg msg;
			s2p8 = msg[1];
			voiceGroup.set(\s2p8, s2p8);
		});
		this.addCommand(\s2p9, "f", { arg msg;
			s2p9 = msg[1];
			voiceGroup.set(\s2p9, s2p9);
		});
		this.addCommand(\s2p10, "f", { arg msg;
			s2p10 = msg[1];
			voiceGroup.set(\s2p10, s2p10);
		});
		this.addCommand(\s2p11, "f", { arg msg;
			s2p11 = msg[1];
			voiceGroup.set(\s2p11, s2p11);
		});
		this.addCommand(\s2p12, "f", { arg msg;
			s2p12 = msg[1];
			voiceGroup.set(\s2p12, s2p12);
		});
		this.addCommand(\s2p13, "f", { arg msg;
			s2p13 = msg[1];
			voiceGroup.set(\s2p13, s2p13);
		});
		this.addCommand(\s2p14, "f", { arg msg;
			s2p14 = msg[1];
			voiceGroup.set(\s2p14, s2p14);
		});
		this.addCommand(\s2p15, "f", { arg msg;
			s2p15 = msg[1];
			voiceGroup.set(\s2p15, s2p15);
		});
		this.addCommand(\s2p16, "f", { arg msg;
			s2p16 = msg[1];
			voiceGroup.set(\s2p16, s2p16);
		});

		//partials snapshot 3:
		this.addCommand(\s3p1, "f", { arg msg;
			s3p1 = msg[1];
			voiceGroup.set(\s3p1, s3p1);
		});
		this.addCommand(\s3p2, "f", { arg msg;
			s3p2 = msg[1];
			voiceGroup.set(\s3p2, s3p2);
		});
		this.addCommand(\s3p3, "f", { arg msg;
			s3p3 = msg[1];
			voiceGroup.set(\s3p3, s3p3);
		});
		this.addCommand(\s3p4, "f", { arg msg;
			s3p4 = msg[1];
			voiceGroup.set(\s3p4, s3p4);
		});
		this.addCommand(\s3p5, "f", { arg msg;
			s3p5 = msg[1];
			voiceGroup.set(\s3p5, s3p5);
		});
		this.addCommand(\s3p6, "f", { arg msg;
			s3p6 = msg[1];
			voiceGroup.set(\s3p6, s3p6);
		});
		this.addCommand(\s3p7, "f", { arg msg;
			s3p7 = msg[1];
			voiceGroup.set(\s3p7, s3p7);
		});
		this.addCommand(\s3p8, "f", { arg msg;
			s3p8 = msg[1];
			voiceGroup.set(\s3p8, s3p8);
		});
		this.addCommand(\s3p9, "f", { arg msg;
			s3p9 = msg[1];
			voiceGroup.set(\s3p9, s3p9);
		});
		this.addCommand(\s3p10, "f", { arg msg;
			s3p10 = msg[1];
			voiceGroup.set(\s3p10, s3p10);
		});
		this.addCommand(\s3p11, "f", { arg msg;
			s3p11 = msg[1];
			voiceGroup.set(\s3p11, s3p11);
		});
		this.addCommand(\s3p12, "f", { arg msg;
			s3p12 = msg[1];
			voiceGroup.set(\s3p12, s3p12);
		});
		this.addCommand(\s3p13, "f", { arg msg;
			s3p13 = msg[1];
			voiceGroup.set(\s3p13, s3p13);
		});
		this.addCommand(\s3p14, "f", { arg msg;
			s3p14 = msg[1];
			voiceGroup.set(\s3p14, s3p14);
		});
		this.addCommand(\s3p15, "f", { arg msg;
			s3p15 = msg[1];
			voiceGroup.set(\s3p15, s3p15);
		});
		this.addCommand(\s3p16, "f", { arg msg;
			s3p16 = msg[1];
			voiceGroup.set(\s3p16, s3p16);
		});

		//partials snapshot 4:
		this.addCommand(\s4p1, "f", { arg msg;
			s4p1 = msg[1];
			voiceGroup.set(\s4p1, s4p1);
		});
		this.addCommand(\s4p2, "f", { arg msg;
			s4p2 = msg[1];
			voiceGroup.set(\s4p2, s4p2);
		});
		this.addCommand(\s4p3, "f", { arg msg;
			s4p3 = msg[1];
			voiceGroup.set(\s4p3, s4p3);
		});
		this.addCommand(\s4p4, "f", { arg msg;
			s4p4 = msg[1];
			voiceGroup.set(\s4p4, s4p4);
		});
		this.addCommand(\s4p5, "f", { arg msg;
			s4p5 = msg[1];
			voiceGroup.set(\s4p5, s4p5);
		});
		this.addCommand(\s4p6, "f", { arg msg;
			s4p6 = msg[1];
			voiceGroup.set(\s4p6, s4p6);
		});
		this.addCommand(\s4p7, "f", { arg msg;
			s4p7 = msg[1];
			voiceGroup.set(\s4p7, s4p7);
		});
		this.addCommand(\s4p8, "f", { arg msg;
			s4p8 = msg[1];
			voiceGroup.set(\s4p8, s4p8);
		});
		this.addCommand(\s4p9, "f", { arg msg;
			s4p9 = msg[1];
			voiceGroup.set(\s4p9, s4p9);
		});
		this.addCommand(\s4p10, "f", { arg msg;
			s4p10 = msg[1];
			voiceGroup.set(\s4p10, s4p10);
		});
		this.addCommand(\s4p11, "f", { arg msg;
			s4p11 = msg[1];
			voiceGroup.set(\s4p11, s4p11);
		});
		this.addCommand(\s4p12, "f", { arg msg;
			s4p12 = msg[1];
			voiceGroup.set(\s4p12, s4p12);
		});
		this.addCommand(\s4p13, "f", { arg msg;
			s4p13 = msg[1];
			voiceGroup.set(\s4p13, s4p13);
		});
		this.addCommand(\s4p14, "f", { arg msg;
			s4p14 = msg[1];
			voiceGroup.set(\s4p14, s4p14);
		});
		this.addCommand(\s4p15, "f", { arg msg;
			s4p15 = msg[1];
			voiceGroup.set(\s4p15, s4p15);
		});
		this.addCommand(\s4p16, "f", { arg msg;
			s4p16 = msg[1];
			voiceGroup.set(\s4p16, s4p16);
		});

	}

	free {
		voiceGroup.free;
	}
}