/* 
 * File:   ptc06.h
 * Author: VANCHINH
 *
 * Created on August 21, 2015, 3:59 PM
 */

#ifndef PTC06_H
#define	PTC06_H

#define cmd_host   0x56
#define cmd_cmr    0x76

void tx_byte(BYTE p);
void cmr_rx(BYTE cmd, BYTE a1, BYTE a2, BYTE a3);


#endif	/* PTC06_H */

