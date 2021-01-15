#!/usr/bin/python3
import mido
from pynput.keyboard import Key, Controller
keybird = Controller()
notes = {64: Key.up, 75: Key.left, 76: Key.down,77:Key.right, 63:Key.esc, 65:Key.enter}
velocity_press=127
velocity_release=0
o = mido.open_output('CMD Touch:CMD Touch MIDI 1 20:0')
with mido.open_input('CMD Touch:CMD Touch MIDI 1 20:0') as data:
    for msg in data:
        print(msg)
        o.send(msg) # so I can see the key presses
        if msg.note in notes:
            if msg.velocity == velocity_press:
                keybird.press(notes[msg.note])
            else:
                keybird.release(notes[msg.note])

