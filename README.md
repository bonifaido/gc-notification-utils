# gc-notification-utils
Capture real stop-the-world ConcurrentMarkSweep times, instead of wall-clock time of between the beginning of the first CMS phase until the end of the last phase.

With this tool you can measure how long your application staled during the two stop-world-phases of CMS (initial-mark, rescan).

For background see: https://blogs.oracle.com/jonthecollector/entry/the_unspoken_cms_and_printgcdetails

