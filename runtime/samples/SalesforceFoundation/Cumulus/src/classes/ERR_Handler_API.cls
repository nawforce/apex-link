/*
    Copyright (c) 2014, Salesforce.org
    All rights reserved.
    
    Redistribution and use in source and binary forms, with or without
    modification, are permitted provided that the following conditions are met:
    
    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * Neither the name of Salesforce.org nor the names of
      its contributors may be used to endorse or promote products derived
      from this software without specific prior written permission.

    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
    "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT 
    LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS 
    FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE 
    COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, 
    INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, 
    BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
    LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER 
    CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT 
    LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN 
    ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
    POSSIBILITY OF SUCH DAMAGE.
*/

/**
* @author Salesforce.org
* @date 2014
* @group API
* @group-content ../../ApexDocContent/API.htm
* @description API class that exposes error handling.
*/
global class ERR_Handler_API {
    
    global enum Context {
    	/* @description Address context. */
	    ADDR, 
	    /* @description Affiliations context. */
	    AFFL, 
	    /* @description Allocations context. */
	    ALLO, 
	    /* @description Batch Data Entry context. */
	    BDE, 
	    /* @description Batch Data Import context. */
	    BDI, 
	    /* @description Contacts context. */
	    CON, 
	    /* @description Account Conversion context. */
	    CONV, 
	    /* @description Households context. */
	    HH, 
	    /* @description Lead Conversion context. */
	    LD, 
        /* @description Level context. */
        LVL, 
	    /* @description Opportunities context. */
	    OPP, 
	    /* @description Payments context. */
	    PMT, 
	    /* @description Relationships context. */
	    REL, 
	    /* @description Recurring Donations context. */
	    RD,  
	    /* @description Rollups context. */
	    RLLP, 
	    /* @description Schedulable context. */
	    SCH, 
	    /* @description Settings context. */
	    STTG, 
	    /* @description Table-Driven Trigger Management context. */
	    TDTM
    }
     
    /*******************************************************************************************************
    * @description Creates an error record from an exception, stores it in the database (if enabled), and sends error
    *              notifications (if enabled).
    * @param e Exception to create error record from.
    * @return context The context the exception was raised on.
    */
    global static void processError(Exception e, String context) {
        ERR_Handler.processError(e, context);
    }
}