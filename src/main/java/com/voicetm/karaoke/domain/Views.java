package com.voicetm.karaoke.domain;

public final class Views {
    public interface Id {}

    public interface IdName extends Id {}

    public interface FullSong extends IdName {}

    public interface FullComment extends IdName {}

    public interface FullMessage extends IdName {}

    public interface FullProfile extends IdName {}
}
