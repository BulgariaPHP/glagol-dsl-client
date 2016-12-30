<?php
declare(strict_types = 1);
namespace Glagol\CompileClient\Socket\Response;

class InfoMessage extends AbstractMessage
{
    public function toString(): string
    {
        return '<info>' . parent::toString() . '</info>';
    }
}