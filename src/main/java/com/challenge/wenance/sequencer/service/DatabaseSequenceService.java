package com.challenge.wenance.sequencer.service;

import org.springframework.stereotype.Service;

@Service
public interface DatabaseSequenceService {

    long generateSequence(String seqName);
}
